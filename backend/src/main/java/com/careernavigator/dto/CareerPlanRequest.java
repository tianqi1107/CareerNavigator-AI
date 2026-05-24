package com.careernavigator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CareerPlanRequest {

    @NotBlank(message = "专业不能为空")
    private String major;

    @NotBlank(message = "学历不能为空")
    private String education;

    private String skills;

    private String interests;

    private String city;

    private String targetCompanyType;

    private String additionalInfo;
}
