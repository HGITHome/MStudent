package com.dgut.main.entity.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.dgut.main.entity.Admin;
import com.dgut.main.entity.Major;
import com.dgut.member.entity.Member;
import com.dgut.member.entity.StudentInfo;

public abstract class BaseClass implements Serializable {

	/**
	 * 班级类
	 */
	private static final long serialVersionUID = 1L;

	private int hashcode = Integer.MIN_VALUE;
	
	private Integer id;
	
	private String className;
	
	private String grade;
	
	private Integer classNum;
	
	private Major major;
	
	private Date register_time;
	
	private Admin admin;
	
	private Date lastUpdateTime;
	
	private Set<StudentInfo> studentSet;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getClassNum() {
		return classNum;
	}

	public void setClassNum(Integer classNum) {
		this.classNum = classNum;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}


	public Set<StudentInfo> getStudentSet() {
		return studentSet;
	}

	public void setStudentSet(Set<StudentInfo> studentSet) {
		this.studentSet = studentSet;
	}
	
	
	
	

	public Date getRegister_time() {
		return register_time;
	}

	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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
		BaseClass other = (BaseClass) obj;
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
