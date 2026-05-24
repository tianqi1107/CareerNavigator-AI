package com.careernavigator.ai.provider;

import com.careernavigator.ai.AIProvider;
import com.careernavigator.ai.model.ChatMessage;
import com.careernavigator.ai.model.ChatRequest;
import com.careernavigator.ai.model.ChatResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractAIProvider implements AIProvider {

    protected final RestTemplate restTemplate = new RestTemplate();
    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected abstract String getApiKey();
    protected abstract String getBaseUrl();
    protected abstract String getDefaultModel();

    @Override
    public boolean isAvailable() {
        return getApiKey() != null && !getApiKey().isEmpty() && !getApiKey().equals("xxx") && !getApiKey().equals("sk-xxx");
    }

    @Override
    public ChatResponse chat(ChatRequest request) {
        String url = getBaseUrl() + "/chat/completions";
        String model = request.getModel() != null ? request.getModel() : getDefaultModel();

        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("messages", request.getMessages().stream()
                .map(m -> Map.of("role", m.getRole(), "content", m.getContent()))
                .collect(Collectors.toList()));
        body.put("temperature", request.getTemperature() != null ? request.getTemperature() : 0.7);
        body.put("max_tokens", request.getMaxTokens() != null ? request.getMaxTokens() : 2000);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getApiKey());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());

            String content = root.path("choices").get(0).path("message").path("content").asText();
            int tokensUsed = root.path("usage").path("total_tokens").asInt(0);
            String responseModel = root.path("model").asText(model);

            return ChatResponse.builder()
                    .content(content)
                    .model(responseModel)
                    .tokensUsed(tokensUsed)
                    .provider(getName())
                    .build();
        } catch (Exception e) {
            log.error("AI provider {} call failed: {}", getName(), e.getMessage());
            throw new RuntimeException("AI service call failed: " + e.getMessage(), e);
        }
    }
}
