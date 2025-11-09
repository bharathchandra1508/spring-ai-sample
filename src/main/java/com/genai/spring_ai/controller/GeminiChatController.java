package com.genai.spring_ai.controller;

import com.genai.spring_ai.AIPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeminiChatController
{
    private final ChatClient chatClient;

    public GeminiChatController(ChatClient.Builder builder)
    {
        this.chatClient = builder.build();
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody AIPrompt aiPrompt)
    {
        return chatClient.prompt()
                .user(aiPrompt.getPrompt())
                .call()
                .chatResponse();
    }
}
