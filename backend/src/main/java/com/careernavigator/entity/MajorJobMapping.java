package com.careernavigator.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("major_job_mapping")
public class MajorJobMapping {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long majorId;

    private Long jobCategoryId;

    private Integer relevanceScore;

    private String scoreFactors;

    private String recommendationLevel;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private String majorName;

    @TableField(exist = false)
    private String categoryName;
}
