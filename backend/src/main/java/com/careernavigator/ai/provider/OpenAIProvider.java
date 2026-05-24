package com.careernavigator.ai.provider;

import com.careernavigator.ai.AIProviderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenAIProvider extends AbstractAIProvider {

    private final AIProviderRegistry registry;

    @Value("${app.ai.providers.openai.api-key:}")
    private String apiKey;

    @Value("${app.ai.providers.openai.base-url:https://api.openai.com/v1}")
    private String baseUrl;

    @Value("${app.ai.providers.openai.model:gpt-4o}")
    private String model;

    @PostConstruct
    public void init() {
        registry.register(this);
    }

    @Override
    public String getName() {
        return "openai";
    }

    @Override
    protected String getApiKey() {
        return apiKey;
    }

    @Override
    protected String getBaseUrl() {
        return baseUrl;
    }

    @Override
    protected String getDefaultModel() {
        return model;
    }
}
