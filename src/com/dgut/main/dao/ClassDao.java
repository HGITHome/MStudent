package com.dgut.main.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.BClass;

public interface ClassDao {
	
	public Pagination getPage(String queryDepartmentName,String queryMajorName,String queryClassName,Integer pageNo,Integer pageSize);
	
	public List<BClass> getList();
	
	public BClass findById(Integer id);
	
	public BClass save(BClass bean);
	
	public BClass updateByUpdater(Updater<BClass> updater);
	
	public BClass deleteById(Integer id);
}
