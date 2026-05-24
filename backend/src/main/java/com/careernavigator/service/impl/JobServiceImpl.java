package com.careernavigator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.careernavigator.common.exception.BusinessException;
import com.careernavigator.common.result.ResultCode;
import com.careernavigator.entity.Job;
import com.careernavigator.entity.JobCategory;
import com.careernavigator.mapper.JobMapper;
import com.careernavigator.mapper.JobCategoryMapper;
import com.careernavigator.service.JobService;
import com.careernavigator.vo.JobVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobMapper jobMapper;
    private final JobCategoryMapper jobCategoryMapper;

    @Override
    public IPage<JobVO> searchJobs(String keyword, Long categoryId, String city,
                                   String educationReq, String companyType,
                                   Integer page, Integer size) {
        Page<Job> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Job> wrapper = new LambdaQueryWrapper<Job>()
                .eq(Job::getStatus, 1);

        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w
                    .like(Job::getJobName, keyword)
                    .or().like(Job::getCompanyName, keyword)
                    .or().like(Job::getDescription, keyword)
            );
        }
        if (categoryId != null) {
            wrapper.eq(Job::getCategoryId, categoryId);
        }
        if (StringUtils.hasText(city)) {
            wrapper.eq(Job::getCity, city);
        }
        if (StringUtils.hasText(educationReq)) {
            wrapper.eq(Job::getEducationReq, educationReq);
        }
        if (StringUtils.hasText(companyType)) {
            wrapper.eq(Job::getCompanyType, companyType);
        }
        wrapper.orderByDesc(Job::getHotScore);

        IPage<Job> jobPage = jobMapper.selectPage(pageParam, wrapper);
        return jobPage.convert(this::toVO);
    }

    @Override
    @Cacheable(value = "job", key = "#id")
    public JobVO getJobDetail(Long id) {
        Job job = jobMapper.selectById(id);
        if (job == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        // Increment view count
        job.setViewCount(job.getViewCount() + 1);
        jobMapper.updateById(job);

        JobVO vo = toVO(job);
        // Get category name
        JobCategory category = jobCategoryMapper.selectById(job.getCategoryId());
        if (category != null) {
            vo.setCategoryName(category.getName());
        }
        return vo;
    }

    @Override
    @Cacheable(value = "hotJobs", key = "#limit")
    public List<JobVO> getHotJobs(int limit) {
        List<Job> jobs = jobMapper.selectList(
                new LambdaQueryWrapper<Job>()
                        .eq(Job::getStatus, 1)
                        .orderByDesc(Job::getHotScore)
                        .last("LIMIT " + limit)
        );
        return jobs.stream().map(job -> {
            JobVO vo = toVO(job);
            JobCategory category = jobCategoryMapper.selectById(job.getCategoryId());
            if (category != null) vo.setCategoryName(category.getName());
            return vo;
        }).collect(Collectors.toList());
    }

    private JobVO toVO(Job job) {
        List<String> skills = Collections.emptyList();
        if (StringUtils.hasText(job.getSkillsRequired())) {
            try {
                skills = Arrays.asList(job.getSkillsRequired()
                        .replace("[", "").replace("]", "").replace("\"", "").split(","));
            } catch (Exception ignored) {}
        }
        return JobVO.builder()
                .id(job.getId())
                .jobName(job.getJobName())
                .companyName(job.getCompanyName())
                .companyType(job.getCompanyType())
                .salaryMin(job.getSalaryMin())
                .salaryMax(job.getSalaryMax())
                .city(job.getCity())
                .province(job.getProvince())
                .educationReq(job.getEducationReq())
                .experienceReq(job.getExperienceReq())
                .skillsRequired(skills)
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .benefits(job.getBenefits())
                .isCivilService(job.getIsCivilService())
                .isStateOwned(job.getIsStateOwned())
                .publishDate(job.getPublishDate())
                .deadline(job.getDeadline())
                .viewCount(job.getViewCount())
                .hotScore(job.getHotScore())
                .build();
    }
}
