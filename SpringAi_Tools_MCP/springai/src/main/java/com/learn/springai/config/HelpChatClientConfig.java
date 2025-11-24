package com.learn.springai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class HelpChatClientConfig {

@Value("classpath:helpPromptTemplate.st")
Resource systemPromptTemplate;

@Bean("helpChatClient")
public ChatClient helpChatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
    Advisor loggerAdvisor = new SimpleLoggerAdvisor();
    Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
    return builder
            .defaultSystem(systemPromptTemplate)
            .defaultAdvisors(loggerAdvisor, memoryAdvisor)
            .build();
}
}
