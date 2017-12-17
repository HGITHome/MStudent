package com.dgut.member.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.member.dao.StudentInfoDao;
import com.dgut.member.entity.StudentInfo;

@Repository
public class StudentInfoDaoImpl extends HibernateBaseDao<StudentInfo, Integer> implements StudentInfoDao {

	
	public Pagination getPage(String queryMajorName,String queryStudentName,
			String queryStudentNumber, Integer pageNo, Integer pageSize) {
	    Finder f = Finder.create("select bean from StudentInfo bean where 1=1");
	    if(!StringUtils.isBlank(queryMajorName)){
	    	f.append(" and bean.major.name like:name");
	    	f.setParam("name", "%"+queryMajorName+"%");
	    }
	    
	    if(!StringUtils.isBlank(queryStudentName)){
	    	f.append(" and bean.name like:name");
	    	f.setParam("name", "%"+queryStudentName+"%");
	    }
	    if(!StringUtils.isBlank(queryStudentNumber)){
	    	f.append(" and bean.schoolNumber =:schoolNumber");
	    	f.setParam("schoolNumber", queryStudentNumber);
	    }
	    f.append(" order by bean.schoolNumber asc");
		return find(f,pageNo,pageSize);
	}

	


	
	public StudentInfo findById(Integer id) {
		StudentInfo bean = super.get(id);
		return bean;
	}

	
	public StudentInfo findBySchoolNumber(String schoolNumber) {
		String hql = "select bean from StudentInfo bean where 1=1 and bean.schoolNumber =:?";
		return  (StudentInfo) findUnique(hql, schoolNumber);
	}

	public List<StudentInfo> getListByClassId(Integer classId){
		String hql = "select bean from StudentInfo bean where 1=1 and bean.bclass.id =:id order by bean.schoolNumber asc";
		List<StudentInfo> list = getSession().createQuery(hql).setParameter("id", classId).list();
		return list;
	}
	
	public StudentInfo save(StudentInfo bean) {
		getSession().save(bean);
		return bean;
	}

	
	public StudentInfo deleteById(Integer id) {
		StudentInfo bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	public StudentInfo[] deleteByIds(Integer[] ids) {
		StudentInfo[] students = new StudentInfo[ids.length];
		for(int i = 0 ; i < ids.length ; i ++){
			students[i] = deleteById(ids[i]);
		}
		return students;
	}

	
	protected Class<StudentInfo> getEntityClass() {
		
		return StudentInfo.class;
	}

}
