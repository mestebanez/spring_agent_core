package com.example.agent.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class SupportAgent {
    private final ChatClient client;
    private final ToolCallbackProvider provider;
    public SupportAgent(ChatClient client, @Autowired(required = false) @Qualifier( "mcp") ToolCallbackProvider provider) {
        this.client = client;
        this.provider = provider;
    }

    public String ask(String question) {
        // optionally routes to MCP
        if (provider != null) {
            // we could stream here is need be
            return client.prompt().user(question).tools(provider).call().content();
        } else {
            return client.prompt().user(question).call().content();
        }
    }
}
