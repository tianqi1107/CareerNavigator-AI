package com.careernavigator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.careernavigator.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface JobMapper extends BaseMapper<Job> {

    @Select("SELECT city, COUNT(*) as count FROM job WHERE status = 1 GROUP BY city ORDER BY count DESC LIMIT #{limit}")
    List<Map<String, Object>> selectCityDistribution(@Param("limit") int limit);

    @Select("SELECT company_type as companyType, AVG((salary_min + salary_max) / 2) as avgSalary " +
            "FROM job WHERE status = 1 AND salary_min IS NOT NULL GROUP BY company_type")
    List<Map<String, Object>> selectSalaryByCompanyType();

    @Select("SELECT DATE_FORMAT(publish_date, '%Y-%m') as month, COUNT(*) as count " +
            "FROM job WHERE status = 1 AND publish_date IS NOT NULL " +
            "GROUP BY month ORDER BY month DESC LIMIT #{limit}")
    List<Map<String, Object>> selectJobTrends(@Param("limit") int limit);
}
