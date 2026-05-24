package com.careernavigator.service;

import com.careernavigator.entity.JobCategory;
import java.util.List;

public interface JobCategoryService {

    List<JobCategory> getCategoryTree();

    List<JobCategory> getSubCategories(Long parentId);
}
