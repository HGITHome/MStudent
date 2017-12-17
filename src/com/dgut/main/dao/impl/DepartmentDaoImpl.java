package com.dgut.main.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.main.dao.DepartmentDao;
import com.dgut.main.entity.Department;

@Repository
public class DepartmentDaoImpl extends HibernateBaseDao<Department, Integer> implements DepartmentDao {

	
	public List<Department> getList() {
		String hql="select bean from Department bean where 1=1";
		return find(hql);
	}

	
	public Department findById(Integer id) {
	    Department bean = super.get(id);
		return bean;
	}

	
	public Department save(Department bean) {
		getSession().save(bean);
		return bean;
	}

	
	public Department deleteById(Integer id) {
		Department bean  = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	protected Class<Department> getEntityClass() {
		
		return Department.class;
	}

}
