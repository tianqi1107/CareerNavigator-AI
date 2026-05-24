package com.careernavigator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.careernavigator.entity.Major;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MajorMapper extends BaseMapper<Major> {

    @Select("SELECT m.*, GROUP_CONCAT(jc.name) as related_jobs " +
            "FROM major m " +
            "LEFT JOIN major_job_mapping mjm ON m.id = mjm.major_id " +
            "LEFT JOIN job_category jc ON mjm.job_category_id = jc.id " +
            "WHERE m.id = #{id} " +
            "GROUP BY m.id")
    Map<String, Object> selectMajorDetail(@Param("id") Long id);
}
