package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AIService {

    @Value("${cohere.api.key}")
    private String cohereApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAIResponse(String message, String category) {

        message = message.toLowerCase();

        String response = "";

        // CASUAL WEAR
        if (category.equalsIgnoreCase("casual")) {
            if (message.contains("summer")) {
                response = "Try light cotton t-shirts, denim shorts, and sneakers for a cool summer look";
            } else if (message.contains("winter")) {
                response = "Go for hoodies, jackets, and jeans with boots for a cozy winter style";
            } else {
                response = "Casual style tip: Pair a basic t-shirt with jeans and sneakers for a clean everyday look";
            }
        }

        // FORMAL WEAR
        else if (category.equalsIgnoreCase("formal")) {
            if (message.contains("interview")) {
                response = "Wear a well-fitted suit or formal shirt with trousers. Keep colors neutral like black, navy, or grey";
            } else if (message.contains("office")) {
                response = "Opt for formal shirts, blazers, and polished shoes for a professional office look 💼";
            } else {
                response = "Formal style tip: Stick to clean cuts, solid colors, and minimal accessories";
            }
        }

        // PARTY / EVENT WEAR
        else if (category.equalsIgnoreCase("party") || category.equalsIgnoreCase("event")) {
            if (message.contains("night")) {
                response = "For night parties, wear dark outfits like black dresses or shirts with stylish shoes";
            } else if (message.contains("wedding")) {
                response = "Go for ethnic wear or elegant outfits like saree, lehenga, or suit for weddings ";
            } else {
                response = "Party tip: Choose bold colors, stylish fits, and statement pieces to stand out ";
            }
        }

        // ACCESSORIES
        else if (category.equalsIgnoreCase("accessories")) {
            if (message.contains("watch")) {
                response = "A classic watch can elevate any outfit instantly ";
            } else if (message.contains("bag")) {
                response = "Choose a stylish handbag or backpack that matches your outfit ";
            } else if (message.contains("jewelry")) {
                response = "Minimal jewelry works best for a classy and elegant look ";
            } else {
                response = "Accessories tip: Don't overdo it—keep it simple and stylish ";
            }
        }

        // GENERAL
        else {
            if (message.contains("hello") || message.contains("hi")) {
                response = "Hi there! How can I help you with fashion today?";
            } else if (message.contains("trend")) {
                response = "Current trend: Oversized fits, neutral tones, and sustainable fashion ";
            } else if (message.contains("color")) {
                response = "Neutral colors like beige, white, and black are always in style ";
            } else {
                response = "DEFAULT"; // triggers AI
            }
        }

        // CALL COHERE ONLY IF DEFAULT
        if (response.equals("DEFAULT")) {
            return callCohere(message);
        }

        return response;
    }

    // COHERE API METHOD (FIXED)
    private String callCohere(String userMessage) {
        try {
            String url = "https://api.cohere.ai/v1/chat";

            Map<String, Object> body = new HashMap<>();
            body.put("message", userMessage);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + cohereApiKey);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, Map.class);

            // Cohere returns "text"
            return response.getBody().get("text").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Sorry, AI is not available right now ";
        }
    }
}