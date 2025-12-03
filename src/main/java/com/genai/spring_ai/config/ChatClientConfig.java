package com.genai.spring_ai.config;

import com.genai.spring_ai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig
{
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder)
    {
        /*
        ChatOptions chatOptions = ChatOptions.builder()
                .model("gemini-2.0-flash-001")
                .maxTokens(100)
                .temperature(0.8)
                .build();
         */

        return chatClientBuilder
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(),
                                        new TokenUsageAuditAdvisor()))
                //.defaultOptions(chatOptions)
                .defaultSystem("""
                        You are a travel agent. Your role is to help users with questions related to holidays and travel.\s
                        kindly inform them that you can only assist with queries related to travel and holidays.
                        """)
                .build();
    }
}
