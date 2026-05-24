package com.careernavigator.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("major")
public class Major {

    @TableId(type = IdType.AUTO)
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

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
