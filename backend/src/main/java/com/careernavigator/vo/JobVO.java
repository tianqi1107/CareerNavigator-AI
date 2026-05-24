package com.careernavigator.vo;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class JobVO {
    private Long id;
    private String jobName;
    private String companyName;
    private String companyType;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private String city;
    private String province;
    private String educationReq;
    private String experienceReq;
    private List<String> skillsRequired;
    private String description;
    private String requirements;
    private String benefits;
    private Integer isCivilService;
    private Integer isStateOwned;
    private LocalDate publishDate;
    private LocalDate deadline;
    private String categoryName;
    private Integer viewCount;
    private Integer hotScore;
}
