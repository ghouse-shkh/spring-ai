package com.learn.springai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learn.springai.tools.DateTimeTool;

@Configuration
public class ChatClientConfig {

    @Bean("dateTimeChatClient")
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory, DateTimeTool dateTimeTool) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return builder
                .defaultAdvisors(loggerAdvisor, memoryAdvisor)
                .defaultTools(dateTimeTool)
                .build();
    }
}
