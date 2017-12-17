package com.dgut.main.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.CourseDao;
import com.dgut.main.entity.Course;

@Repository
public class CourseDaoImpl extends HibernateBaseDao<Course, Integer> implements CourseDao {

	
	public Pagination getPage(String queryCourseName, Integer pageNo,
			Integer pageSize) {
		Finder f = Finder.create("select bean from Course bean where 1=1");
		if(!StringUtils.isBlank(queryCourseName)){
			f.append(" and bean.courseName like:name");
			f.setParam("name", "%"+queryCourseName+"%");
		}
		f.append(" order by bean.id asc");
		return find(f,pageNo,pageSize);
	}

	
	public Course findById(Integer id) {
		Course bean = super.get(id);
		return bean;
	}

	public List<Course> getList(){
		String hql = "from Course bean order by bean.id asc";
		return find(hql);
	}
	
	public Course findByCourseName(String courseName) {
		String hql = "select bean from Course bean where bean.courseName =:name";
		return (Course) findUnique(hql, courseName);
	}

	
	public Course save(Course bean) {
		getSession().save(bean);
		return bean;
	}

	
	public Course deleteById(Integer id) {
		Course bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	public Course deleteByCourseName(String courseName) {
		Course bean = findByCourseName(courseName);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	protected Class<Course> getEntityClass() {
		
		return Course.class;
	}

}
