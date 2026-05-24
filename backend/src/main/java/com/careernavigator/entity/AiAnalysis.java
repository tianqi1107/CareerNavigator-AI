package com.careernavigator.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("ai_analysis")
public class AiAnalysis {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String analysisType;

    private String inputData;

    private String outputData;

    private String aiProvider;

    private String aiModel;

    private Integer tokensUsed;

    private Integer responseTime;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
