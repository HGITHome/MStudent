package com.dgut.main.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.main.dao.MajorDao;
import com.dgut.main.entity.Major;

@Repository
public class MajorDaoImpl extends HibernateBaseDao<Major, Integer> implements MajorDao {

	
	public List<Major> getList() {
		String hql="select bean from Major bean where 1=1";
		return find(hql);
	}

	
	public Major findById(Integer id) {
		Major major = super.get(id);
		return major;
	}

	
	public Major save(Major bean) {
		getSession().save(bean);
		return bean;
	}

	
	public Major deleteById(Integer id) {
		Major bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	protected Class<Major> getEntityClass() {
		
		return Major.class;
	}

}
