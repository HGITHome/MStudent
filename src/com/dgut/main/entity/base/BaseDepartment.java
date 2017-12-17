package com.dgut.main.entity.base;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.dgut.main.entity.Major;

public abstract class BaseDepartment implements Serializable {

	/**
	 * 院系类
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;
	
	private Integer priority;
	
	private Set<Major> majorSet;

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

	public Set<Major> getMajorSet() {
		return majorSet;
	}

	public void setMajorSet(Set<Major> majorSet) {
		this.majorSet = majorSet;
	}


	
	
	

}
