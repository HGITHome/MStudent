package com.dgut.main.entity.base;

import java.io.Serializable;

import com.dgut.main.entity.CourseCategory;
import com.dgut.main.entity.Department;

public abstract class BaseCourse implements Serializable {

	/**
	 * 课程类
	 */
	private static final long serialVersionUID = 1L;
	
	private int hashcode = Integer.MIN_VALUE;

	private Integer id;
	
	private String courseName;
	
	private double creditHour;
	
	private Integer courseHour;
	
	private Department department;

	private CourseCategory courseCategory;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}



	public double getCreditHour() {
		return creditHour;
	}

	public void setCreditHour(double creditHour) {
		this.creditHour = creditHour;
	}

	public Integer getCourseHour() {
		return courseHour;
	}

	public void setCourseHour(Integer courseHour) {
		this.courseHour = courseHour;
	}



	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	
	public CourseCategory getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(CourseCategory courseCategory) {
		this.courseCategory = courseCategory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hashcode;
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
		BaseCourse other = (BaseCourse) obj;
		if (hashcode != other.hashcode)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
