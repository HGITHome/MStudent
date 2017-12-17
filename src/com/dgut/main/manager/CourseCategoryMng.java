package com.dgut.main.manager;

import java.util.List;

import com.dgut.main.entity.CourseCategory;

public interface CourseCategoryMng {

   public List<CourseCategory> getList();
	
	public CourseCategory findById(Integer id);
	
	public CourseCategory save(CourseCategory bean);
	
	public CourseCategory update(CourseCategory bean);
	
	public CourseCategory deleteById(Integer id);
}
