package com.careernavigator.vo;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class MajorVO {
    private Long id;
    private String majorCode;
    private String majorName;
    private String category;
    private String firstLevel;
    private String description;
    private String employmentDirection;
    private BigDecimal avgSalary;
    private BigDecimal employmentRate;
    private Integer hotScore;
    private List<String> relatedJobs;
}
