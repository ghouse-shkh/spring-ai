package com.learn.springai.config;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.learn.springai.rag.WebSearchDocumentRetriever;

@Configuration
public class RagChatClientConfig {

        @Bean("vectorStoreChatClient")
        public ChatClient vectorStoreChatClient(ChatClient.Builder builder, ChatMemory chatMemory,
                        @Qualifier("vectorAdvisor") RetrievalAugmentationAdvisor vectorRetrievalAugmentationAdvisor) {

                Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
                Advisor loggerAdvisor = new SimpleLoggerAdvisor();

                return builder
                                .defaultAdvisors(List.of(memoryAdvisor, loggerAdvisor,
                                                vectorRetrievalAugmentationAdvisor))
                                .build();
        }

        @Bean("webSearchChatClient")
        public ChatClient webSearchChatClient(ChatClient.Builder builder, ChatMemory chatMemory,
                        @Qualifier("webSearchAdvisor") RetrievalAugmentationAdvisor webRetrievalAugmentationAdvisor) {
                Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
                Advisor loggerAdvisor = new SimpleLoggerAdvisor();

                return builder
                                .defaultAdvisors(List.of(memoryAdvisor, loggerAdvisor, webRetrievalAugmentationAdvisor))
                                .build();
        }

        @Bean("vectorAdvisor")
        public RetrievalAugmentationAdvisor vectorAdvisor(VectorStore vectorStore) {
                return RetrievalAugmentationAdvisor.builder().documentRetriever(
                                VectorStoreDocumentRetriever.builder().vectorStore(vectorStore)
                                                .topK(3)
                                                .similarityThreshold(0.5)
                                                .build())
                                .build();
        }

        @Bean("webSearchAdvisor")
        public RetrievalAugmentationAdvisor webSearchAdvisor(RestClient.Builder restClientBuilder) {
                return RetrievalAugmentationAdvisor.builder().documentRetriever(
                                WebSearchDocumentRetriever.builder()
                                                .restClientBuilder(restClientBuilder)
                                                .resultLimit(5)
                                                .build())
                                .build();
        }

}
