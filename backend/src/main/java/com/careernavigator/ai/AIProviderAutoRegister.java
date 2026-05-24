package com.careernavigator.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AIProviderAutoRegister implements CommandLineRunner {

    private final AIProviderRegistry registry;

    @Override
    public void run(String... args) {
        log.info("=== AI Provider Registration Summary ===");
        log.info("Available providers: {}", registry.getAvailableProviders());
        log.info("Default provider: {}", registry.getDefaultProvider().getName());
        log.info("=========================================");
    }
}
