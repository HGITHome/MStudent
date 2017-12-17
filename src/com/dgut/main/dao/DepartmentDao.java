package com.dgut.main.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.main.entity.Department;

public interface DepartmentDao {
	
	public List<Department> getList();
	
	public Department findById(Integer id);
	
	public Department save(Department bean);
	
	public Department updateByUpdater(Updater<Department> updater);
	
	public Department deleteById(Integer id);
}
