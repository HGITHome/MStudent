package com.dgut.main.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.NoticeDao;
import com.dgut.main.entity.Notice;

@Repository
public class NoticeDaoImpl extends HibernateBaseDao<Notice, Integer> implements NoticeDao {

	
	public Pagination getPage(String queryTitle, String queryUsername,
			Integer pageNo, Integer pageSize) {
		Finder f = Finder.create("select bean from Notice bean where 1=1");
		if(!StringUtils.isBlank(queryTitle)){
			f.append(" and bean.title like:title");
			f.setParam("title", "%"+queryTitle+"%");
		}
		if(!StringUtils.isBlank(queryUsername)){
			f.append(" and bean.admin.username like:username");
			f.setParam("username", "%"+queryUsername+"%");
		}
		f.append(" order by bean.id desc");
		return find(f,pageNo,pageSize);
	}


	public Notice findById(Integer id) {
		Notice bean = super.get(id);
		return bean;
	}

	public Notice save(Notice bean) {
		getSession().save(bean);
		return bean;
	}


	public Notice deleteById(Integer id) {
		Notice bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}


	protected Class<Notice> getEntityClass() {
		
		return Notice.class;
	}

}
