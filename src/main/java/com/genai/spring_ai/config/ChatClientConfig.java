package com.genai.spring_ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig
{
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder)
    {
        return chatClientBuilder.build();
    }
}
