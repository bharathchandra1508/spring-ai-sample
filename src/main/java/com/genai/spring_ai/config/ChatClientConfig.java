package com.genai.spring_ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig
{
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder)
    {
        return chatClientBuilder
                .defaultSystem("""
                        You are a travel agent. Your role is to help users with questions related to holidays and travel.\s
                        kindly inform them that you can only assist with queries related to travel and holidays.
                        """)
                .build();
    }
}
