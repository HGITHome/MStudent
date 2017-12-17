package com.dgut.main.manager;

import java.util.List;


import com.dgut.common.page.Pagination;

import com.dgut.main.entity.Course;

public interface CourseMng {
public Pagination getPage(String queryCourseName,Integer pageNo,Integer pageSize);
	
	public Course findById(Integer id);
	
	public List<Course> getList();
	
	public Course findByCourseName(String courseName);
	
	public Course save(Course bean);
	
	public Course update(Course bean);
	
	public Course deleteById(Integer id);
	
	public Course deleteByCourseName(String courseName);
	
}
