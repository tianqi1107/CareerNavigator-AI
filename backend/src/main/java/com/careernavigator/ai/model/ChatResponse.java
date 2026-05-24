package com.careernavigator.ai.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatResponse {
    private String content;
    private String model;
    private Integer tokensUsed;
    private String provider;
}
