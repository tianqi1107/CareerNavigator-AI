package com.careernavigator.ai.provider;

import com.careernavigator.ai.AIProviderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class XiaomiMiMoProvider extends AbstractAIProvider {

    private final AIProviderRegistry registry;

    @Value("${app.ai.providers.mimo.api-key:}")
    private String apiKey;

    @Value("${app.ai.providers.mimo.base-url:https://api.xiaomi.com/v1}")
    private String baseUrl;

    @Value("${app.ai.providers.mimo.model:mimo-chat}")
    private String model;

    @PostConstruct
    public void init() {
        registry.register(this);
    }

    @Override
    public String getName() {
        return "mimo";
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
