package com.careernavigator.ai;

import com.careernavigator.ai.model.ChatRequest;
import com.careernavigator.ai.model.ChatResponse;

public interface AIProvider {

    String getName();

    ChatResponse chat(ChatRequest request);

    boolean isAvailable();
}
