package com.learn.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateTimeController {

    private final ChatClient dateTimeChatClient;

    public DateTimeController(@Qualifier("dateTimeChatClient") ChatClient dateTimeChatClient) {
        this.dateTimeChatClient = dateTimeChatClient;
    }

    @GetMapping("/chat/tool/time")
    public ResponseEntity<String> getCurrentDateTime(@RequestHeader("username") String username, @RequestParam("message") String message) {
        String answer = dateTimeChatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();

        return ResponseEntity.ok(answer);
    }

    

}
