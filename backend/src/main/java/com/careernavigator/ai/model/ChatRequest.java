package com.careernavigator.ai.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ChatRequest {
    private List<ChatMessage> messages;
    private Double temperature;
    private Integer maxTokens;
    private String model;
}
