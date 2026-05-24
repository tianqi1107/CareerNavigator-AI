package com.careernavigator.ai.prompt;

import com.careernavigator.dto.CareerPlanRequest;

public class CareerPlanPrompt {

    public static final String SYSTEM_PROMPT = """
            你是一位资深的大学生职业规划师和就业指导专家，拥有以下能力：
            1. 深入了解中国各行业就业市场和岗位需求
            2. 熟悉公务员、国企、央企、事业单位、私企、外企等各类用人单位的招聘特点
            3. 掌握各专业的就业方向和发展前景
            4. 能够根据个人情况提供个性化的职业规划建议

            请根据用户提供的信息，从以下维度给出详细分析：
            - 推荐岗位方向（至少3-5个，按匹配度排序）
            - 学习路线建议（短期1年内、中期1-3年、长期3-5年）
            - 技能短板分析（需要补充的技能和学习资源）
            - 是否建议考研（分析利弊）
            - 是否适合考公（分析适配度）
            - 岗位竞争分析（竞争激烈程度、优势劣势）

            请使用清晰的结构化格式输出，便于阅读。
            """;

    public static String buildPrompt(CareerPlanRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("请根据以下信息为我进行职业规划分析：\n\n");
        sb.append("【基本信息】\n");
        sb.append("- 专业：").append(request.getMajor()).append("\n");
        sb.append("- 学历：").append(request.getEducation()).append("\n");

        if (request.getSkills() != null && !request.getSkills().isEmpty()) {
            sb.append("- 技能：").append(request.getSkills()).append("\n");
        }
        if (request.getInterests() != null && !request.getInterests().isEmpty()) {
            sb.append("- 兴趣方向：").append(request.getInterests()).append("\n");
        }
        if (request.getCity() != null && !request.getCity().isEmpty()) {
            sb.append("- 期望工作城市：").append(request.getCity()).append("\n");
        }
        if (request.getTargetCompanyType() != null && !request.getTargetCompanyType().isEmpty()) {
            sb.append("- 目标企业类型：").append(request.getTargetCompanyType()).append("\n");
        }
        if (request.getAdditionalInfo() != null && !request.getAdditionalInfo().isEmpty()) {
            sb.append("- 其他信息：").append(request.getAdditionalInfo()).append("\n");
        }

        sb.append("\n请给出详细的职业规划分析报告。");
        return sb.toString();
    }
}
