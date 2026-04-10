package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.model.ChatMessage;

@Service
public class ChatStorageService {

    private Map<String, List<ChatMessage>> chatHistory = new HashMap<>();

    public void saveMessage(String chatId, String userMsg, String botMsg) {

        chatHistory.putIfAbsent(chatId, new ArrayList<>());

        ChatMessage msg = new ChatMessage();
        msg.setChatId(chatId);
        msg.setUserMessage(userMsg);
        msg.setBotResponse(botMsg);

        chatHistory.get(chatId).add(msg);
    }

    public List<ChatMessage> getChat(String chatId) {
        return chatHistory.getOrDefault(chatId, new ArrayList<>());
    }

    public Set<String> getAllChats() {
        return chatHistory.keySet();
    }
}