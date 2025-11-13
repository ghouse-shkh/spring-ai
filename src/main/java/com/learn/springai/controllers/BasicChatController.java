package com.learn.springai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.springai.advisors.TokenUsageAuditAdvisor;

@RestController
@RequestMapping
public class BasicChatController {

    private final ChatClient chatClient;

    public BasicChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("chat")
    public String chat() {
        return chatClient
                .prompt()
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }

    @GetMapping("chat/basic")
    public String basicChat(@RequestParam String prompt) {
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }

    @GetMapping("chat/boot")
    public String chatWithSystemMessage(@RequestParam String prompt) {
        String systemMessage = """
                You are an expert in Spring Boot and Java programming.
                You will answer the user's questions accordingly.
                Any unrelated questions should be politely declined.
                """;
        return chatClient
                .prompt()
                .advisors(new TokenUsageAuditAdvisor())
                .system(systemMessage) //overriding default system message
                .user(prompt)
                .call()
                .content();
    }

}