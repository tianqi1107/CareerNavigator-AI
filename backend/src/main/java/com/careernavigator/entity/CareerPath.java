package com.careernavigator.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("career_path")
public class CareerPath {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long majorId;

    private Long jobCategoryId;

    private String pathName;

    private String pathStages;

    private Integer avgYearsToSenior;

    private String difficultyLevel;

    private String requiredCerts;

    private String suggestedEducation;

    private String description;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
