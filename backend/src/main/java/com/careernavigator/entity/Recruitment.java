package com.careernavigator.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("recruitment")
public class Recruitment {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String company;

    private Long categoryId;

    private String positions;

    private Integer headcount;

    private String city;

    private String educationReq;

    private String majorReq;

    private String salaryRange;

    private LocalDate publishDate;

    private LocalDate deadline;

    private String source;

    private String sourceUrl;

    private String content;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
