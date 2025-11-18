package com.learn.springai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RAGController {

        private final ChatClient vectorStoreChatClient;
        private final ChatClient webSearchChatClient;
        private final VectorStore vectorStore;

        public RAGController(@Qualifier("vectorStoreChatClient") ChatClient vectorStoreChatClient,
                        @Qualifier("webSearchChatClient") ChatClient webSearchChatClient,
                        VectorStore vectorStore) {
                this.vectorStoreChatClient = vectorStoreChatClient;
                this.webSearchChatClient = webSearchChatClient;
                this.vectorStore = vectorStore;
        }

        @Value("classpath:promptTemplates/systemPromptDataRAG.st")
        Resource promptTemplate;

        @Value("classpath:promptTemplates/systemPromptProduct.st")
        Resource productPromptTemplate;

        @GetMapping("/rag/ask")
        public ResponseEntity<String> askQuestionWithRAG(@RequestHeader("username") String username,
                        @RequestParam String question) {

                /*
                 * 1. Perform similarity search in the vector store to get relevant documents
                 * 2. Construct the prompt by combining the retrieved documents with the user's
                 * question
                 * 3. Use the ChatClient to get the answer from the LLM
                 * 4. Return the answer as the response
                 */
                // SearchRequest searchRequest = SearchRequest.builder().query(question)
                // .topK(3).similarityThreshold(0.5).build();
                // List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
                // String similarContext = similarDocs.stream().map(Document::getText)
                // .collect(Collectors.joining(System.lineSeparator()));

                String answer = vectorStoreChatClient.prompt()
                                // .system(promptTemplateSpec -> promptTemplateSpec.text(productPromptTemplate)
                                // .param("documents", similarContext))
                                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                                .user(question)
                                .call()
                                .content();

                return ResponseEntity.ok(answer);
        }

        @GetMapping("/rag/doc/ask")
        public ResponseEntity<String> askWithRAGDocument(@RequestHeader("username") String username,
                        @RequestParam String question) {

                String answer = vectorStoreChatClient.prompt()
                                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                                .user(question)
                                .call()
                                .content();

                return ResponseEntity.ok(answer);
        }

        @GetMapping("/rag/web/ask")
        public ResponseEntity<String> askWithWebRAG(@RequestHeader("username") String username,
                        @RequestParam String question) {

                String answer = webSearchChatClient.prompt()
                                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                                .user(question)
                                .call()
                                .content();

                return ResponseEntity.ok(answer);
        }
}
