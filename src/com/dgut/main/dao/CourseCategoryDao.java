package com.dgut.main.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.main.entity.CourseCategory;

public interface CourseCategoryDao {
	
	public List<CourseCategory> getList();
	
	public CourseCategory findById(Integer id);
	
	public CourseCategory save(CourseCategory bean);
	
	public CourseCategory updateByUpdater(Updater<CourseCategory> updater);
	
	public CourseCategory deleteById(Integer id);
}
