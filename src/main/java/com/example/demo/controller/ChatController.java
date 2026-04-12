package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ChatMessage;
import com.example.demo.service.AIService;
import com.example.demo.service.ChatStorageService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ChatController {

    private final AIService aiService;
    private final ChatStorageService storage;

    public ChatController(AIService aiService, ChatStorageService storage) {
        this.aiService = aiService;
        this.storage = storage;
    }

    //CHAT API (with chatId support)
    @PostMapping("/chat")
    public String chat(@RequestBody Map<String, String> request) {

        String message = request.get("message");
        String category = request.get("category");
        String chatId = request.get("chatId");

        if (message == null || message.isEmpty()) {
            return "Please enter a message!";
        }

        if (category == null || category.isEmpty()) {
            category = "general";
        }

        if (chatId == null || chatId.isEmpty()) {
            chatId = "default_chat";
        }

        // Get AI / Manual response
        String response = aiService.getAIResponse(message, category);

        // Save to history
        storage.saveMessage(chatId, message, response);

        return response;
    }

    // GET FULL CHAT HISTORY
    @GetMapping("/history/{chatId}")
    public List<ChatMessage> getChat(@PathVariable String chatId) {
        return storage.getChat(chatId);
    }

    // GET ALL CHAT IDS (RECENT CHATS)
    @GetMapping("/allChats")
    public Set<String> getAllChats() {
        return storage.getAllChats();
    }

    // TEST API
    @GetMapping("/test")
    public String test() {
        return "Backend working!";
    }
}