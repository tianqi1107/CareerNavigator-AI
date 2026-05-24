package com.careernavigator.controller;

import com.careernavigator.common.result.R;
import com.careernavigator.entity.JobCategory;
import com.careernavigator.service.JobCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "岗位分类", description = "岗位分类相关接口")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;

    @Operation(summary = "获取分类树")
    @GetMapping("/tree")
    public R<List<JobCategory>> getCategoryTree() {
        return R.ok(jobCategoryService.getCategoryTree());
    }

    @Operation(summary = "获取子分类")
    @GetMapping("/{parentId}/children")
    public R<List<JobCategory>> getSubCategories(@PathVariable Long parentId) {
        return R.ok(jobCategoryService.getSubCategories(parentId));
    }
}
