package com.dgut.main.entity.base;

import java.io.Serializable;

import com.dgut.main.entity.BClass;
import com.dgut.main.entity.Course;
import com.dgut.main.entity.Major;

public abstract class BaseClassCourse implements Serializable {

	/**
	 * 班级课表
	 */
	private static final long serialVersionUID = 1L;

	private int hascode = Integer.MIN_VALUE;
	
	private Integer id;
	
	private Course course;
	
	private Major major;
	
	private BClass bclass;
	
	private Integer term;
	
	private Integer isInput;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public BClass getBclass() {
		return bclass;
	}

	public void setBclass(BClass bclass) {
		this.bclass = bclass;
	}


	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getIsInput() {
		return isInput;
	}

	public void setIsInput(Integer isInput) {
		this.isInput = isInput;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hascode;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseClassCourse other = (BaseClassCourse) obj;
		if (hascode != other.hascode)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
