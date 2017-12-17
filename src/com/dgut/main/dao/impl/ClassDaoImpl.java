package com.dgut.main.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.ClassDao;
import com.dgut.main.entity.BClass;

@Repository
public class ClassDaoImpl extends HibernateBaseDao<BClass, Integer> implements ClassDao {

	
	public Pagination getPage(String queryDepartmentName,String queryMajorName,String queryClassName, Integer pageNo,
			Integer pageSize) {
		Finder f = Finder.create("select bean from BClass bean where 1=1");
		if(!StringUtils.isBlank(queryDepartmentName)){
			f.append(" and bean.major.department.name like:name");
			f.setParam("name", "%"+queryDepartmentName+"%");
		}
		if(!StringUtils.isBlank(queryMajorName)){
			f.append(" and bean.major.name like:name");
			f.setParam("name", "%"+queryMajorName+"%");
		}
		if(!StringUtils.isBlank(queryClassName)){
			f.append(" and bean.className like:className");
			f.setParam("className", queryClassName);
		}
		f.append(" order by bean.id asc");
		return find(f,pageNo,pageSize);
	}


	public List<BClass> getList() {
		String hql = "from BClass bean order by bean.id asc";
		return find(hql);
	}

	public List<Object[]> getClassInfo(){
		String hql = "select bean.bclass,count(*) from StudentInfo bean group by bean.bclass order by bean.id asc";
		List<Object[]> list = getSession().createQuery(hql).list();
		return list;
	}
	
	
	public BClass findById(Integer id) {
		BClass bean = super.get(id);
		return bean;
	}

	
	public BClass save(BClass bean) {
		getSession().save(bean);
		return bean;
	}

	
	public BClass deleteById(Integer id) {
		BClass bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	protected Class<BClass> getEntityClass() {
		
		return BClass.class;
	}

}
