package com.dgut.main.entity.base;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.dgut.main.entity.BClass;
import com.dgut.main.entity.Department;
 
public abstract class BaseMajor implements Serializable {

	/**
	 * 专业类
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;
	
	private Integer priority;
	
	private Department department;
	
	private Set<BClass> classSet;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<BClass> getClassSet() {
		return classSet;
	}

	public void setClassSet(Set<BClass> classSet) {
		this.classSet = classSet;
	}


	
}
