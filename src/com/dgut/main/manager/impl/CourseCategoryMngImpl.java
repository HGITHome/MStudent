package com.dgut.main.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.main.dao.CourseCategoryDao;
import com.dgut.main.entity.CourseCategory;
import com.dgut.main.manager.CourseCategoryMng;

@Service
@Transactional
public class CourseCategoryMngImpl implements CourseCategoryMng {


	@Transactional(readOnly=true)
	public List<CourseCategory> getList() {
		
		return dao.getList();
	}

	@Transactional(readOnly=true)
	public CourseCategory findById(Integer id) {
		
		return dao.findById(id);
	}

	
	public CourseCategory save(CourseCategory bean) {
		
		return dao.save(bean);
	}

	
	public CourseCategory update(CourseCategory bean){
		Updater<CourseCategory> updater = new Updater<CourseCategory>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public CourseCategory deleteById(Integer id) {
		
		return dao.deleteById(id);
	}

	@Autowired
	private CourseCategoryDao dao;
}
