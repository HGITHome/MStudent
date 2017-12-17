package com.dgut.main.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.hibernate3.Updater;
import com.dgut.main.dao.CourseCategoryDao;
import com.dgut.main.entity.CourseCategory;

@Repository
public class CourseCategoryDaoImpl extends HibernateBaseDao<CourseCategory, Integer> implements
		CourseCategoryDao {

	
	@SuppressWarnings("unchecked")
	public List<CourseCategory> getList() {
		String hql = "select bean from CourseCategory bean order by bean.id asc";
		return getSession().createQuery(hql).list();
	}


	public CourseCategory findById(Integer id) {
		CourseCategory bean = super.get(id);
		return bean;
	}

	
	public CourseCategory save(CourseCategory bean) {
		getSession().save(bean);
		return bean;
	}

	
	
	public CourseCategory deleteById(Integer id) {
		CourseCategory bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}
	protected Class<CourseCategory> getEntityClass() {
		
		return CourseCategory.class;
	}

}
