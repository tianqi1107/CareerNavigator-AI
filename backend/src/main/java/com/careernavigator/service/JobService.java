package com.careernavigator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.careernavigator.vo.JobVO;
import java.util.List;

public interface JobService {

    IPage<JobVO> searchJobs(String keyword, Long categoryId, String city,
                            String educationReq, String companyType,
                            Integer page, Integer size);

    JobVO getJobDetail(Long id);

    List<JobVO> getHotJobs(int limit);
}
