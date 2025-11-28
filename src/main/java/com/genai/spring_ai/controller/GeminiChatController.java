package com.genai.spring_ai.controller;

import com.genai.spring_ai.model.AIPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/api")
public class GeminiChatController
{
    private final ChatClient chatClient;

    public GeminiChatController(ChatClient.Builder builder)
    {
        this.chatClient = builder.build();
    }

    @PostMapping("/chat")
    public String chat(@RequestBody AIPrompt aiPrompt)
    {
        return chatClient
                .prompt()
                .system("""
                        You are a travel agent. Your role is to help users with questions related to holidays and travel.\s
                        kindly inform them that you can only assist with queries related to travel and holidays.
                        """)
                .user(aiPrompt.getPrompt())
                .call()
                .content();
    }
}
