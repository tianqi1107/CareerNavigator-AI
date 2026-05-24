package com.careernavigator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.careernavigator.ai.AIProviderRegistry;
import com.careernavigator.ai.model.ChatMessage;
import com.careernavigator.ai.model.ChatRequest;
import com.careernavigator.ai.model.ChatResponse;
import com.careernavigator.ai.prompt.CareerPlanPrompt;
import com.careernavigator.common.exception.BusinessException;
import com.careernavigator.common.result.ResultCode;
import com.careernavigator.dto.AiChatRequest;
import com.careernavigator.dto.CareerPlanRequest;
import com.careernavigator.entity.AiAnalysis;
import com.careernavigator.mapper.AiAnalysisMapper;
import com.careernavigator.service.AiCareerService;
import com.careernavigator.vo.CareerPlanVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiCareerServiceImpl implements AiCareerService {

    private final AIProviderRegistry providerRegistry;
    private final AiAnalysisMapper aiAnalysisMapper;
    private final ObjectMapper objectMapper;

    @Override
    public CareerPlanVO careerPlan(Long userId, CareerPlanRequest request) {
        long startTime = System.currentTimeMillis();
        String providerName = providerRegistry.getDefaultProvider().getName();

        try {
            String prompt = CareerPlanPrompt.buildPrompt(request);
            ChatMessage systemMsg = new ChatMessage("system", CareerPlanPrompt.SYSTEM_PROMPT);
            ChatMessage userMsg = new ChatMessage("user", prompt);

            ChatRequest chatRequest = ChatRequest.builder()
                    .messages(Arrays.asList(systemMsg, userMsg))
                    .temperature(0.7)
                    .maxTokens(2000)
                    .build();

            ChatResponse response = providerRegistry.getDefaultProvider().chat(chatRequest);
            long responseTime = System.currentTimeMillis() - startTime;

            // Save analysis record
            AiAnalysis analysis = new AiAnalysis();
            analysis.setUserId(userId);
            analysis.setAnalysisType("CAREER_PLAN");
            analysis.setInputData(toJson(request));
            analysis.setOutputData(response.getContent());
            analysis.setAiProvider(providerName);
            analysis.setAiModel(response.getModel());
            analysis.setTokensUsed(response.getTokensUsed());
            analysis.setResponseTime((int) responseTime);
            analysis.setStatus(1);
            analysis.setCreatedAt(LocalDateTime.now());
            aiAnalysisMapper.insert(analysis);

            return CareerPlanVO.builder()
                    .overallAdvice(response.getContent())
                    .aiProvider(providerName)
                    .build();

        } catch (Exception e) {
            log.error("AI career plan failed: ", e);
            throw new BusinessException(ResultCode.AI_PROVIDER_ERROR);
        }
    }

    @Override
    public String chat(Long userId, AiChatRequest request) {
        long startTime = System.currentTimeMillis();
        String providerName = request.getProvider() != null
                ? request.getProvider()
                : providerRegistry.getDefaultProvider().getName();

        try {
            ChatMessage systemMsg = new ChatMessage("system",
                    "你是一个专业的大学生就业指导顾问。请根据用户的问题，提供专业、实用的就业指导建议。回答要简洁明了，有针对性。");
            ChatMessage userMsg = new ChatMessage("user", request.getMessage());

            ChatRequest chatRequest = ChatRequest.builder()
                    .messages(Arrays.asList(systemMsg, userMsg))
                    .temperature(0.7)
                    .maxTokens(1500)
                    .build();

            ChatResponse response = providerRegistry.getProvider(providerName).chat(chatRequest);
            long responseTime = System.currentTimeMillis() - startTime;

            // Save record
            AiAnalysis analysis = new AiAnalysis();
            analysis.setUserId(userId);
            analysis.setAnalysisType("CHAT");
            analysis.setInputData(request.getMessage());
            analysis.setOutputData(response.getContent());
            analysis.setAiProvider(providerName);
            analysis.setAiModel(response.getModel());
            analysis.setTokensUsed(response.getTokensUsed());
            analysis.setResponseTime((int) responseTime);
            analysis.setStatus(1);
            analysis.setCreatedAt(LocalDateTime.now());
            aiAnalysisMapper.insert(analysis);

            return response.getContent();

        } catch (Exception e) {
            log.error("AI chat failed: ", e);
            throw new BusinessException(ResultCode.AI_PROVIDER_ERROR);
        }
    }

    @Override
    public IPage<AiAnalysis> getHistory(Long userId, String type, Integer page, Integer size) {
        Page<AiAnalysis> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<AiAnalysis> wrapper = new LambdaQueryWrapper<AiAnalysis>()
                .eq(AiAnalysis::getUserId, userId);
        if (type != null) {
            wrapper.eq(AiAnalysis::getAnalysisType, type);
        }
        wrapper.orderByDesc(AiAnalysis::getCreatedAt);
        return aiAnalysisMapper.selectPage(pageParam, wrapper);
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return obj.toString();
        }
    }
}
