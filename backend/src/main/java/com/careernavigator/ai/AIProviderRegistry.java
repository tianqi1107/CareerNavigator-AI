package com.careernavigator.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AIProviderRegistry {

    private final Map<String, AIProvider> providers = new ConcurrentHashMap<>();

    @Value("${app.ai.default-provider:deepseek}")
    private String defaultProviderName;

    public void register(AIProvider provider) {
        providers.put(provider.getName(), provider);
        log.info("Registered AI provider: {}", provider.getName());
    }

    public AIProvider getProvider(String name) {
        AIProvider provider = providers.get(name);
        if (provider == null) {
            log.warn("AI provider not found: {}, falling back to default", name);
            return getDefaultProvider();
        }
        if (!provider.isAvailable()) {
            log.warn("AI provider {} is not available, falling back to default", name);
            return getDefaultProvider();
        }
        return provider;
    }

    public AIProvider getDefaultProvider() {
        AIProvider provider = providers.get(defaultProviderName);
        if (provider == null || !provider.isAvailable()) {
            // Try any available provider
            return providers.values().stream()
                    .filter(AIProvider::isAvailable)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No available AI provider"));
        }
        return provider;
    }

    public List<String> getAvailableProviders() {
        return providers.values().stream()
                .filter(AIProvider::isAvailable)
                .map(AIProvider::getName)
                .collect(Collectors.toList());
    }

    public List<String> getAllProviders() {
        return new ArrayList<>(providers.keySet());
    }
}
