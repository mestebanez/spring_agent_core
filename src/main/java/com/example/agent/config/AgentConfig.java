package com.example.agent.config;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.spec.McpClientTransport;
import io.swagger.v3.oas.annotations.callbacks.Callback;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AgentConfig {
    @Bean
    ChatClient chatClient(ChatModel model) {
        return ChatClient.builder(model).build();
    }

    @Bean
    @Primary
    McpSyncClient getSyncClient() {
        McpClientTransport transport =
                HttpClientStreamableHttpTransport.builder(
                                "http://localhost:8081/mcp")
                        .build();

        return McpClient.sync(transport)
                        .build();
    }

    @Bean("mcp")
    @ConditionalOnProperty(prefix = "mcp", name = "disable", havingValue = "false")
    ToolCallbackProvider mcpTools(
            McpSyncClient client) {
        return SyncMcpToolCallbackProvider.builder()
                .addMcpClient(client)
                .build();
    }
}
