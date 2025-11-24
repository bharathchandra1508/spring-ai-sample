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
    public ChatResponse chat(@RequestBody AIPrompt aiPrompt)
    {
        return chatClient.prompt()
                .user(aiPrompt.getPrompt())
                .call()
                .chatResponse();
    }
}
