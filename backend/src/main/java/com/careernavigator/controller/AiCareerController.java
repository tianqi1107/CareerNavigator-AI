package com.careernavigator.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.careernavigator.common.result.R;
import com.careernavigator.common.utils.SecurityUtils;
import com.careernavigator.dto.AiChatRequest;
import com.careernavigator.dto.CareerPlanRequest;
import com.careernavigator.entity.AiAnalysis;
import com.careernavigator.service.AiCareerService;
import com.careernavigator.vo.CareerPlanVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AI职业规划", description = "AI分析相关接口")
@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiCareerController {

    private final AiCareerService aiCareerService;

    @Operation(summary = "AI职业规划分析")
    @PostMapping("/career-plan")
    public R<CareerPlanVO> careerPlan(@Valid @RequestBody CareerPlanRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        return R.ok(aiCareerService.careerPlan(userId, request));
    }

    @Operation(summary = "AI聊天问答")
    @PostMapping("/chat")
    public R<String> chat(@Valid @RequestBody AiChatRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        return R.ok(aiCareerService.chat(userId, request));
    }

    @Operation(summary = "AI分析历史")
    @GetMapping("/history")
    public R<IPage<AiAnalysis>> getHistory(
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Long userId = SecurityUtils.getCurrentUserId();
        return R.ok(aiCareerService.getHistory(userId, type, page, size));
    }
}
