package com.careernavigator.ai.provider;

import com.careernavigator.ai.AIProviderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZhipuAIProvider extends AbstractAIProvider {

    private final AIProviderRegistry registry;

    @Value("${app.ai.providers.zhipu.api-key:}")
    private String apiKey;

    @Value("${app.ai.providers.zhipu.base-url:https://open.bigmodel.cn/api/paas/v4}")
    private String baseUrl;

    @Value("${app.ai.providers.zhipu.model:glm-4}")
    private String model;

    @PostConstruct
    public void init() {
        registry.register(this);
    }

    @Override
    public String getName() {
        return "zhipu";
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
