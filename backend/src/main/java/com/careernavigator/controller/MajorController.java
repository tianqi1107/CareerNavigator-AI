package com.careernavigator.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.careernavigator.common.result.R;
import com.careernavigator.service.MajorService;
import com.careernavigator.vo.JobVO;
import com.careernavigator.vo.MajorVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "专业管理", description = "专业查询相关接口")
@RestController
@RequestMapping("/api/majors")
@RequiredArgsConstructor
public class MajorController {

    private final MajorService majorService;

    @Operation(summary = "搜索专业")
    @GetMapping
    public R<IPage<MajorVO>> searchMajors(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return R.ok(majorService.searchMajors(keyword, page, size));
    }

    @Operation(summary = "专业详情")
    @GetMapping("/{id}")
    public R<MajorVO> getMajorDetail(@PathVariable Long id) {
        return R.ok(majorService.getMajorDetail(id));
    }

    @Operation(summary = "热门专业")
    @GetMapping("/hot")
    public R<List<MajorVO>> getHotMajors(@RequestParam(defaultValue = "10") int limit) {
        return R.ok(majorService.getHotMajors(limit));
    }

    @Operation(summary = "专业关联岗位推荐")
    @GetMapping("/{id}/jobs")
    public R<List<JobVO>> getRelatedJobs(@PathVariable Long id) {
        return R.ok(majorService.getRelatedJobs(id));
    }

    @Operation(summary = "根据专业查询可报考岗位")
    @GetMapping("/search-jobs")
    public R<List<JobVO>> searchJobsByMajor(
            @RequestParam String major,
            @RequestParam(required = false) String type) {
        return R.ok(majorService.searchJobsByMajor(major, type));
    }
}
