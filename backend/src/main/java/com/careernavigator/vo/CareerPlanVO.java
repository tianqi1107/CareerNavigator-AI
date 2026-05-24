package com.careernavigator.vo;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CareerPlanVO {
    private String recommendedJobs;
    private String learningPath;
    private String skillGaps;
    private String gradSchoolAdvice;
    private String civilServiceAdvice;
    private String competitionAnalysis;
    private String overallAdvice;
    private String aiProvider;
}
