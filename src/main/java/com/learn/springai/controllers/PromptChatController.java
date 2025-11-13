package com.learn.springai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.springai.advisors.TokenUsageAuditAdvisor;

@RestController

public class PromptChatController {

    private final ChatClient chatClient;

    public PromptChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Value("classpath:promptTemplates/template1.st")
    Resource promptTemplate;

    @Value("classpath:promptTemplates/systemPrompt.st")
    Resource systemPrompt;

    @GetMapping("learn")
    public String promptTemplateChat(@RequestParam String topic, @RequestParam String language) {
        return chatClient
                .prompt()
                .system(systemPrompt)
                .user(promptTemplateSpec -> promptTemplateSpec.text(promptTemplate)
                        .param("topic", topic)
                        .param("language", language))
                .call()
                .content();
    }

    @GetMapping("options")
    public String chatOptionsDemo(@RequestParam String topic) {
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .model("llama-3.1-8b-instant")
                .temperature(0.7)
                .maxTokens(100)
                .build();

        String promptTemplate = "Tell me a joke about {topic}.";

        return chatClient
                .prompt()
                .options(chatOptions)
                .advisors(new TokenUsageAuditAdvisor())
                .system("you are a Joke bot. tell a funny joke about the topic provided.")
                .user(promptTemplateSpec -> promptTemplateSpec.text(promptTemplate)
                        .param("topic", topic)
                )
                .call()
                .content();
    }

}