package com.dgut.main.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.dao.ClassDao;
import com.dgut.main.entity.BClass;
import com.dgut.main.manager.ClassMng;

@Service
@Transactional
public class ClassMngImpl implements ClassMng {

	
	@Transactional(readOnly=true)
	public Pagination getPage(String queryDepartmentName,String queryMajorName,String queryClassName, Integer pageNo,
			Integer pageSize) {
		
		return dao.getPage(queryDepartmentName,queryMajorName,queryClassName, pageNo, pageSize);
	}

	@Transactional(readOnly=true)
	public List<BClass> getList() {
		
		return dao.getList();
	}

	@Transactional(readOnly=true)
	public BClass findById(Integer id) {
		
		return dao.findById(id);
	}

	
	public BClass save(BClass bean) {
		
		return dao.save(bean);
	}

	
	public BClass update(BClass bean) {
		Updater<BClass> updater  = new Updater<BClass>(bean);
		return dao.updateByUpdater(updater);
	}


	public BClass deleteById(Integer id) {
		
		return dao.deleteById(id);
	}
	
	@Autowired
	private ClassDao dao;

}
