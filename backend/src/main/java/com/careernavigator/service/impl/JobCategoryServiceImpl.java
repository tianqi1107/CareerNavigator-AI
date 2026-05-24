package com.careernavigator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.careernavigator.entity.JobCategory;
import com.careernavigator.mapper.JobCategoryMapper;
import com.careernavigator.service.JobCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobCategoryServiceImpl implements JobCategoryService {

    private final JobCategoryMapper jobCategoryMapper;

    @Override
    @Cacheable(value = "categoryTree")
    public List<JobCategory> getCategoryTree() {
        List<JobCategory> all = jobCategoryMapper.selectList(
                new LambdaQueryWrapper<JobCategory>()
                        .eq(JobCategory::getStatus, 1)
                        .orderByAsc(JobCategory::getSortOrder)
        );
        // Build tree
        List<JobCategory> roots = all.stream()
                .filter(c -> c.getParentId() == 0 || c.getParentId() == null)
                .collect(Collectors.toList());

        for (JobCategory root : roots) {
            root.setChildren(all.stream()
                    .filter(c -> root.getId().equals(c.getParentId()))
                    .collect(Collectors.toList()));
        }
        return roots;
    }

    @Override
    public List<JobCategory> getSubCategories(Long parentId) {
        return jobCategoryMapper.selectList(
                new LambdaQueryWrapper<JobCategory>()
                        .eq(JobCategory::getParentId, parentId)
                        .eq(JobCategory::getStatus, 1)
                        .orderByAsc(JobCategory::getSortOrder)
        );
    }
}
