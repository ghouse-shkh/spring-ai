package com.learn.springai.rag;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

public class WebSearchDocumentRetriever implements DocumentRetriever {

    private static final Logger logger = LoggerFactory.getLogger(WebSearchDocumentRetriever.class);
    private static final String TAVILY_API_URL = "https://api.tavily.com/search";
    private static final int DEFAULT_RESULT_LIMIT = 5;
    private final int resultLimit;
    private final RestClient restClient;

    public WebSearchDocumentRetriever(RestClient.Builder clientBuilder, int resultLimit) {
        String apiKey = System.getenv("TAVILY_API_KEY");
        this.restClient = clientBuilder
                .baseUrl(TAVILY_API_URL)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();

        if (resultLimit <= 0) {
            this.resultLimit = DEFAULT_RESULT_LIMIT;
        } else {
            this.resultLimit = resultLimit;
        }
    }

    @Value("${tavily.api.key}")
    private static String TAVILY_API_KEY;

    @Override
    public List<Document> retrieve(Query query) {
        logger.info("Performing web search for query: {}", query.text());

        String searchQuery = query.text();
        TavilyResponsePayload response = restClient.post()
                .body(new TavilyRequestPayload(searchQuery, "advanced", resultLimit))
                .retrieve()
                .body(TavilyResponsePayload.class);

        if (response == null || response.results().isEmpty()) {
            logger.info("No results found for query: {}", searchQuery);
            return List.of();
        }

        List<Document> documents = response.results().stream()
                .map(result -> Document.builder()
                        .text(result.content())
                        .metadata("title", result.title())
                        .metadata("url", result.url())
                        .metadata("score", result.score().toString())
                        .build())
                .toList();
        
        logger.info("Retrieved {} documents for query: {}", documents.size(), searchQuery);
        logger.debug("Documents: {}", documents);

        return documents;
    }

    record TavilyRequestPayload(String query, String searchDepth, int maxResults) {
    }

    record TavilyResponsePayload(List<TavilySearchResult> results) {
        record TavilySearchResult(String title, String url, String content, Double score) {
        }
    }

    public static Builder builder() {
      return new Builder();
   }

    // Builder for WebSearchDocumentRetriever
    public static class Builder {
        private RestClient.Builder clientBuilder;
        private int resultLimit = DEFAULT_RESULT_LIMIT;

        private Builder() {
        }

        public Builder restClientBuilder(RestClient.Builder clientBuilder) {
            this.clientBuilder = clientBuilder;
            return this;
        }

        public Builder resultLimit(int resultLimit) {
            this.resultLimit = resultLimit;
            return this;
        }

        public WebSearchDocumentRetriever build() {
            return new WebSearchDocumentRetriever(clientBuilder, resultLimit);
        }
    }
}