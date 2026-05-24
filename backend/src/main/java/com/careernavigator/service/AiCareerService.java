package com.careernavigator.service;

import com.careernavigator.dto.AiChatRequest;
import com.careernavigator.dto.CareerPlanRequest;
import com.careernavigator.entity.AiAnalysis;
import com.careernavigator.vo.CareerPlanVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface AiCareerService {

    CareerPlanVO careerPlan(Long userId, CareerPlanRequest request);

    String chat(Long userId, AiChatRequest request);

    IPage<AiAnalysis> getHistory(Long userId, String type, Integer page, Integer size);
}
