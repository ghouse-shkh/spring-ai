package com.learn.springai.controller;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.springai.tools.HelpTool;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/help")
@Slf4j
public class HelpController {

    private ChatClient helpChatClient;
    private final HelpTool helpTool;

    public HelpController(@Qualifier("helpChatClient") ChatClient helpChatClient, HelpTool helpTool) {
        this.helpChatClient = helpChatClient;
        this.helpTool = helpTool;
    }

    @GetMapping
    public ResponseEntity<String> help(@RequestHeader("username") String username, @RequestParam("message") String message) {
        log.info("Received help request from user: {}", username);
        String answer = helpChatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .tools(helpTool)
                .toolContext(Map.of("username",username))
                .call()
                .content();

        return ResponseEntity.ok(answer);
    }
}
