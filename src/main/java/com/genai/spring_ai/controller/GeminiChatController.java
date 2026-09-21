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

}
