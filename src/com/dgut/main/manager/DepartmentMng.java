package com.dgut.main.manager;

import java.util.List;
import java.util.Set;

import com.dgut.main.entity.Department;
import com.dgut.main.entity.Major;

public interface DepartmentMng {
	
public List<Department> getList();
	
	public Department findById(Integer id);
	
	public Department save(Department bean);
	
	public Department update(Department bean);
	
	public Department deleteById(Integer id);

	public String getDepartmentString(List<Major> majorList);
}
