package com.dgut.main.manager;

import java.util.List;


import com.dgut.common.page.Pagination;
import com.dgut.main.entity.BClass;

public interface ClassMng {

   public Pagination getPage(String queryDepartmentName,String queryMajorName,String queryClassName,Integer pageNo,Integer pageSize);
	
	public List<BClass> getList();
	
	public BClass findById(Integer id);
	
	public BClass save(BClass bean);
	
	public BClass update(BClass bean);
	
	public BClass deleteById(Integer id);
}
