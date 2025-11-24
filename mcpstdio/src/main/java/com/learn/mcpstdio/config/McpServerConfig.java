package com.learn.mcpstdio.config;

import java.util.List;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learn.mcpstdio.tools.FavMovieTool;

@Configuration
public class McpServerConfig {

    @Bean
    public List<ToolCallback> toolCallbacks(FavMovieTool favMovieTool) {
        return List.of(ToolCallbacks.from(favMovieTool));
    }
}
