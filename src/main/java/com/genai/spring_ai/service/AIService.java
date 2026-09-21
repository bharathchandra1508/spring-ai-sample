package com.genai.spring_ai.service;

import com.genai.spring_ai.dto.Joke;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AIService
{
    private final ChatClient chatClient;

    public AIService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String getJoke(String topic)
    {
        String systemPrompt = """
                You are a sarcastic joker, you make poetic jokes in 4 lines.
                You don't make jokes about politics.
                Give me a joke on the topic: {topic}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
        String renderedText = promptTemplate.render(Map.of("topic", topic));

        var response = chatClient.prompt()
                .user(renderedText)
                .advisors(
                        new SimpleLoggerAdvisor()
                )
                .call()
                .entity(Joke.class);
        return response.text();
    }
}
