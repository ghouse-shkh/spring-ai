package com.learn.springai.advisors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;





public class TokenUsageAuditAdvisor implements CallAdvisor{

    private static final Logger logger = LoggerFactory.getLogger(TokenUsageAuditAdvisor.class);

    @Override
    public String getName() {
        return "TokenUsageAuditAdvisor";
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
       ChatClientResponse response = callAdvisorChain.nextCall(chatClientRequest);
       ChatResponse chatResponse = response.chatResponse();

       if(chatResponse.getMetadata() != null){
        String model = chatResponse.getMetadata().getModel();
        logger.info("Model Used: {}", model);
          Usage usage = chatResponse.getMetadata().getUsage();
            if(usage != null){
                logger.info("Prompt Tokens Used: {}", usage.getPromptTokens());
                logger.info("Completion Tokens Used: {}", usage.getCompletionTokens());
                logger.info("Total Tokens Used: {}", usage.getTotalTokens());
            }
       } 
       return response;
    }

}
