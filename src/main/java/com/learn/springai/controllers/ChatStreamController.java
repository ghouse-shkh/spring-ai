package com.learn.springai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class ChatStreamController {

    private final ChatClient chatClient;

    public ChatStreamController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @GetMapping("stream")
    public Flux<String> chatStream(@RequestParam String message) {
        return chatClient
                .prompt()
                .user(message)
                .stream()
                .content();
    }

}
