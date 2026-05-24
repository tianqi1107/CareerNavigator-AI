package com.careernavigator.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.careernavigator.common.result.R;
import com.careernavigator.service.JobService;
import com.careernavigator.vo.JobVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "岗位管理", description = "岗位查询相关接口")
@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @Operation(summary = "搜索岗位")
    @GetMapping
    public R<IPage<JobVO>> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String educationReq,
            @RequestParam(required = false) String companyType,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return R.ok(jobService.searchJobs(keyword, categoryId, city, educationReq, companyType, page, size));
    }

    @Operation(summary = "岗位详情")
    @GetMapping("/{id}")
    public R<JobVO> getJobDetail(@PathVariable Long id) {
        return R.ok(jobService.getJobDetail(id));
    }

    @Operation(summary = "热门岗位")
    @GetMapping("/hot")
    public R<List<JobVO>> getHotJobs(@RequestParam(defaultValue = "10") int limit) {
        return R.ok(jobService.getHotJobs(limit));
    }
}
