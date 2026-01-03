package com.genai.spring_ai.controller;

import com.genai.spring_ai.model.AIPrompt;
import com.genai.spring_ai.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import java.util.List;

@RestController
@RequestMapping("/ai/api")
public class GeminiChatController
{
    private final ChatClient chatClient;

    public GeminiChatController(@Qualifier("chatMemoryChatClient") ChatClient chatClient)
    {
        this.chatClient = chatClient;
    }

    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource userPromptTemplate;

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource systemPromptTemplate;

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

    @GetMapping("/prompt-stuffing")
    public String promptStuffing(@RequestParam("message") String message)
    {
        return chatClient
                .prompt()
                .system(systemPromptTemplate)
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/stream-llm-response")
    public Flux<String> streamAIResponse(@RequestParam("message") String message)
    {
        return chatClient
                .prompt()
                .user(message)
                .stream()
                .content();
    }

    @GetMapping("/structured-ai-response")
    public ResponseEntity<CountryCities> structuredAIResponse(@RequestParam("message") String message)
    {
        CountryCities countryCities = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(CountryCities.class);
        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/list-ai-response")
    public ResponseEntity<List<String>> listAIResponse(@RequestParam("message") String message)
    {
        List<String> countryCities = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ListOutputConverter());
        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/bean-ai-response")
    public ResponseEntity<List<CountryCities>> beanAIResponse(@RequestParam("message") String message)
    {
        List<CountryCities> countryCities = chatClient
                .prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<CountryCities>>() {
                });
        return ResponseEntity.ok(countryCities);
    }

    @GetMapping("/chat-memory")
    public ResponseEntity<String> chatMemory(@RequestHeader("username") String username,
                                                                        @RequestParam("message") String message)
    {
        return ResponseEntity.ok(chatClient.prompt()
                                                .user(message)
                                                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
                                                .call()
                                                .content());
    }
}
