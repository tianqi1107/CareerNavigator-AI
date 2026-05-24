package com.careernavigator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.careernavigator.entity.Major;
import com.careernavigator.vo.JobVO;
import com.careernavigator.vo.MajorVO;
import java.util.List;

public interface MajorService {

    IPage<MajorVO> searchMajors(String keyword, Integer page, Integer size);

    MajorVO getMajorDetail(Long id);

    List<MajorVO> getHotMajors(int limit);

    List<JobVO> getRelatedJobs(Long majorId);
}
