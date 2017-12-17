package com.dgut.main.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.CourseDao;

import com.dgut.main.entity.Course;
import com.dgut.main.manager.CourseMng;

@Service
@Transactional
public class CourseMngImpl implements CourseMng {

	
	@Autowired
	private CourseDao dao;

	
	@Transactional(readOnly=true)
	public Pagination getPage(String queryCoureName, Integer pageNo,
			Integer pageSize) {
		
		return dao.getPage(queryCoureName, pageNo, pageSize);
	}

	@Transactional(readOnly=true)
	public Course findById(Integer id) {
		
		return dao.findById(id);
	}

	@Transactional(readOnly=true)
	public List<Course> getList(){
		return dao.getList();
	}
	
	@Transactional(readOnly=true)
	public Course findByCourseName(String courseName) {
		
		return dao.findByCourseName(courseName);
	}

	
	public Course save(Course bean) {
		
		return dao.save(bean);
	}


	public Course update(Course bean) {
	    Updater<Course> updater = new Updater<Course>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public Course deleteById(Integer id) {
		
		return dao.deleteById(id);
	}

	public Course deleteByCourseName(String courseName) {
		
		return dao.deleteByCourseName(courseName);
	}
}
