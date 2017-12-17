package com.dgut.main.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.main.dao.ClassCourseDao;
import com.dgut.main.entity.BClass;
import com.dgut.main.entity.ClassCourse;

@Repository
public class ClassCourseDaoImpl extends HibernateBaseDao<ClassCourse, Integer> implements
		ClassCourseDao {

	@SuppressWarnings("unchecked")
	public List<ClassCourse> getList() {
		String hql = "select bean from ClassCourse bean where 1=1";
		return find(hql);
	}

	@SuppressWarnings("unchecked")
	public List<ClassCourse> getListByClassBean(BClass bean,Integer term) {
		String hql = "select bean from ClassCourse bean where 1=1 and bean.bclass.id =:id and bean.bclass.major.id=:majorId and bean.term=:term order by bean.term asc";
		List<ClassCourse> list = getSession().createQuery(hql).setParameter("id", bean.getId()).setParameter("majorId", bean.getMajor().getId()).setParameter("term", term).list();
		return list;
	}

	
	public ClassCourse findById(Integer id) {
	    ClassCourse bean = super.get(id);
		return bean;
	}

	@SuppressWarnings("unchecked")
	public List<ClassCourse> getListByClassIdAndTerm(Integer classId,
			Integer term) {
		String hql = "select bean from ClassCourse bean where bean.bclass.id=:id and bean.term =:term";
		List<ClassCourse> list = getSession().createQuery(hql).setParameter("id", classId).setParameter("term", term).list();
		return list;
	}

	
	public ClassCourse findByCourseAndClass(String courseId, String classId){
		String hql = "select bean from ClassCourse bean where bean.course.id=? and bean.bclass.id=?";
		return (ClassCourse) findUnique(hql,Integer.parseInt(courseId),Integer.parseInt(classId));
	}
	
	
	public ClassCourse save(ClassCourse bean) {
		getSession().save(bean);
		return bean;
	}

	
	public ClassCourse deleteById(Integer id) {
		ClassCourse bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}
	
	public ClassCourse delete(ClassCourse bean){
		getSession().delete(bean);
		return bean;
	}
	
	protected Class<ClassCourse> getEntityClass() {
		
		return ClassCourse.class;
	}


}
