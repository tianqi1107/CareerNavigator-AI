package com.careernavigator.controller;

import com.careernavigator.common.result.R;
import com.careernavigator.service.DataVisualizationService;
import com.careernavigator.vo.DashboardVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "数据可视化", description = "仪表盘数据接口")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DataVisualizationService dataVisualizationService;

    @Operation(summary = "获取仪表盘数据")
    @GetMapping
    public R<DashboardVO> getDashboard() {
        return R.ok(dataVisualizationService.getDashboardData());
    }
}
