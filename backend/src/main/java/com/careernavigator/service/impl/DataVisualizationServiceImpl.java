package com.careernavigator.service.impl;

import com.careernavigator.mapper.JobMapper;
import com.careernavigator.mapper.MajorMapper;
import com.careernavigator.service.DataVisualizationService;
import com.careernavigator.vo.DashboardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataVisualizationServiceImpl implements DataVisualizationService {

    private final JobMapper jobMapper;
    private final MajorMapper majorMapper;

    @Override
    @Cacheable(value = "dashboard", key = "'all'")
    public DashboardVO getDashboardData() {
        List<Map<String, Object>> jobTrends = jobMapper.selectJobTrends(12);
        List<Map<String, Object>> cityDistribution = jobMapper.selectCityDistribution(15);
        List<Map<String, Object>> salaryByType = jobMapper.selectSalaryByCompanyType();

        // Summary stats
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalJobs", jobMapper.selectCount(null));
        summary.put("totalMajors", majorMapper.selectCount(null));

        return DashboardVO.builder()
                .jobTrends(jobTrends)
                .cityDistribution(cityDistribution)
                .salaryByType(salaryByType)
                .summary(summary)
                .build();
    }
}
