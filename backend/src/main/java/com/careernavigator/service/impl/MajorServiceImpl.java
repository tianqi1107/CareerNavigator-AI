package com.careernavigator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.careernavigator.common.exception.BusinessException;
import com.careernavigator.common.result.ResultCode;
import com.careernavigator.entity.Major;
import com.careernavigator.entity.MajorJobMapping;
import com.careernavigator.entity.JobCategory;
import com.careernavigator.mapper.MajorMapper;
import com.careernavigator.mapper.MajorJobMappingMapper;
import com.careernavigator.mapper.JobCategoryMapper;
import com.careernavigator.mapper.JobMapper;
import com.careernavigator.entity.Job;
import com.careernavigator.service.MajorService;
import com.careernavigator.vo.JobVO;
import com.careernavigator.vo.MajorVO;
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
public class MajorServiceImpl implements MajorService {

    private final MajorMapper majorMapper;
    private final MajorJobMappingMapper majorJobMappingMapper;
    private final JobCategoryMapper jobCategoryMapper;
    private final JobMapper jobMapper;

    @Override
    public IPage<MajorVO> searchMajors(String keyword, Integer page, Integer size) {
        Page<Major> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Major> wrapper = new LambdaQueryWrapper<Major>()
                .eq(Major::getStatus, 1);

        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w
                    .like(Major::getMajorName, keyword)
                    .or().like(Major::getMajorCode, keyword)
                    .or().like(Major::getCategory, keyword)
            );
        }
        wrapper.orderByDesc(Major::getHotScore);

        IPage<Major> majorPage = majorMapper.selectPage(pageParam, wrapper);
        return majorPage.convert(this::toVO);
    }

    @Override
    @Cacheable(value = "major", key = "#id")
    public MajorVO getMajorDetail(Long id) {
        Major major = majorMapper.selectById(id);
        if (major == null) {
            throw new BusinessException(ResultCode.DATA_NOT_FOUND);
        }
        MajorVO vo = toVO(major);
        // Get related job categories
        List<MajorJobMapping> mappings = majorJobMappingMapper.selectList(
                new LambdaQueryWrapper<MajorJobMapping>()
                        .eq(MajorJobMapping::getMajorId, id)
                        .orderByDesc(MajorJobMapping::getRelevanceScore)
        );
        if (!mappings.isEmpty()) {
            List<String> jobNames = mappings.stream()
                    .map(m -> {
                        JobCategory cat = jobCategoryMapper.selectById(m.getJobCategoryId());
                        return cat != null ? cat.getName() : "";
                    })
                    .filter(StringUtils::hasText)
                    .collect(Collectors.toList());
            vo.setRelatedJobs(jobNames);
        }
        return vo;
    }

    @Override
    @Cacheable(value = "hotMajors", key = "#limit")
    public List<MajorVO> getHotMajors(int limit) {
        List<Major> majors = majorMapper.selectList(
                new LambdaQueryWrapper<Major>()
                        .eq(Major::getStatus, 1)
                        .orderByDesc(Major::getHotScore)
                        .last("LIMIT " + limit)
        );
        return majors.stream().map(this::toVO).collect(Collectors.toList());
    }

    @Override
    public List<JobVO> getRelatedJobs(Long majorId) {
        List<MajorJobMapping> mappings = majorJobMappingMapper.selectList(
                new LambdaQueryWrapper<MajorJobMapping>()
                        .eq(MajorJobMapping::getMajorId, majorId)
                        .orderByDesc(MajorJobMapping::getRelevanceScore)
                        .last("LIMIT 10")
        );
        if (mappings.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> categoryIds = mappings.stream()
                .map(MajorJobMapping::getJobCategoryId)
                .collect(Collectors.toList());

        List<Job> jobs = jobMapper.selectList(
                new LambdaQueryWrapper<Job>()
                        .in(Job::getCategoryId, categoryIds)
                        .eq(Job::getStatus, 1)
                        .orderByDesc(Job::getHotScore)
                        .last("LIMIT 20")
        );
        return jobs.stream().map(this::toJobVO).collect(Collectors.toList());
    }

    private MajorVO toVO(Major major) {
        return MajorVO.builder()
                .id(major.getId())
                .majorCode(major.getMajorCode())
                .majorName(major.getMajorName())
                .category(major.getCategory())
                .firstLevel(major.getFirstLevel())
                .description(major.getDescription())
                .employmentDirection(major.getEmploymentDirection())
                .avgSalary(major.getAvgSalary())
                .employmentRate(major.getEmploymentRate())
                .hotScore(major.getHotScore())
                .build();
    }

    private JobVO toJobVO(Job job) {
        List<String> skills = Collections.emptyList();
        if (StringUtils.hasText(job.getSkillsRequired())) {
            try {
                skills = Arrays.asList(job.getSkillsRequired().replace("[", "").replace("]", "").replace("\"", "").split(","));
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
                .skillsRequired(skills)
                .description(job.getDescription())
                .isCivilService(job.getIsCivilService())
                .isStateOwned(job.getIsStateOwned())
                .publishDate(job.getPublishDate())
                .hotScore(job.getHotScore())
                .build();
    }
}
