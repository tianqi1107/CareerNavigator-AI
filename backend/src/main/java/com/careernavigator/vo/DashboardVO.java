package com.careernavigator.vo;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardVO {
    private List<Map<String, Object>> jobTrends;
    private List<Map<String, Object>> cityDistribution;
    private List<Map<String, Object>> salaryByType;
    private List<Map<String, Object>> majorEmployment;
    private Map<String, Object> summary;
}
