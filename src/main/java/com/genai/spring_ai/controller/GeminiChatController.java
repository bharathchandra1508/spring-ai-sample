package com.genai.spring_ai.controller;

import com.genai.spring_ai.model.AIPrompt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai/api")
public class GeminiChatController
{
    private final ChatClient chatClient;

    public GeminiChatController(ChatClient chatClient)
    {
        this.chatClient = chatClient;
    }

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource userPromptTemplate;

    @PostMapping("/chat")
    public String chat(@RequestBody AIPrompt aiPrompt)
    {
        return chatClient
                .prompt()
                .user(aiPrompt.getPrompt())
                .call()
                .content();
    }

    @GetMapping("/promptTemplate/chat")
    public String chatWithPromptTemplate(@RequestParam("customerName") String customerName,
                                         @RequestParam("customerMessage") String customerMessage )
    {
        return chatClient
                .prompt()
                .system("""
                        You are a professional customer service assistant which helps drafting email responses 
                        to improve the productivity of the customer support team
                        """)
                .user(promptUserSpec -> promptUserSpec.text(userPromptTemplate)
                        .param("customerName",customerName)
                        .param("customerMessage",customerMessage))
                .call()
                .content();
    }
}
