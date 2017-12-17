package com.dgut.main.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.FileDao;
import com.dgut.main.entity.UploadFile;

@Repository
public class FileDaoImpl extends HibernateBaseDao<UploadFile, Integer> implements FileDao {

	
	public Pagination getPage(String queryFileName,Integer pageNo, Integer pageSize) {
		Finder f = Finder.create("select bean from UploadFile bean where 1=1");
		if(!StringUtils.isBlank(queryFileName)){
			f.append(" and bean.fileName like:fileName");
			f.setParam("fileName", "%"+queryFileName+"%");
		}
		f.append(" order by bean.id desc");
		return find(f,pageNo,pageSize);
	}


	public UploadFile findById(Integer id) {
		UploadFile bean = super.get(id);
		return bean;
	}

	
	public UploadFile save(UploadFile bean) {
		getSession().save(bean);
		return bean;
	}


	
	public UploadFile deleteById(Integer id) {
		UploadFile bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}


	
	protected Class<UploadFile> getEntityClass() {
		
		return UploadFile.class;
	}


}
