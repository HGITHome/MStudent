package com.dgut.main.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.Course;

public interface CourseDao {

	public Pagination getPage(String queryCoureName,Integer pageNo,Integer pageSize);
	
	public Course findById(Integer id);
	
	public List<Course> getList();
	
	public Course findByCourseName(String courseName);
	
	public Course save(Course bean);
	
	public Course updateByUpdater(Updater<Course> updater);
	
	public Course deleteById(Integer id);
	
	public Course deleteByCourseName(String courseName);
	
}
