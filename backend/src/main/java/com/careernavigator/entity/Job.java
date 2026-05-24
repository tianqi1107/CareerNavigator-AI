package com.careernavigator.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("job")
public class Job {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long categoryId;

    private String jobName;

    private String companyName;

    private String companyType;

    private BigDecimal salaryMin;

    private BigDecimal salaryMax;

    private String city;

    private String province;

    private String educationReq;

    private String experienceReq;

    private String skillsRequired;

    private String description;

    private String requirements;

    private String benefits;

    private Integer isCivilService;

    private Integer isStateOwned;

    private LocalDate publishDate;

    private LocalDate deadline;

    private String source;

    private String sourceUrl;

    private Integer viewCount;

    private Integer hotScore;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private String categoryName;
}
