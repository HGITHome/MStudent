package com.dgut.main.entity.base;

import java.io.Serializable;

import com.dgut.main.entity.BClass;
import com.dgut.main.entity.Course;
import com.dgut.member.entity.StudentInfo;

public abstract class BaseScore implements Serializable {

	/**
	 * 成绩类
	 */
	private static final long serialVersionUID = 1L;
	
	private int hashcode = Integer.MIN_VALUE;
	
	private Integer id;
	
	private StudentInfo student;
	
	private Course course;
	
	private Integer term;
	
	private double score;
	
	private double point;
	
	private double grade_point;
	
	private BClass bclass;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StudentInfo getStudent() {
		return student;
	}

	public void setStudent(StudentInfo student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	
	
	
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	
	
	
	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public double getGrade_point() {
		return grade_point;
	}

	public void setGrade_point(double grade_point) {
		this.grade_point = grade_point;
	}

	public BClass getBclass() {
		return bclass;
	}

	public void setBclass(BClass bclass) {
		this.bclass = bclass;
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
		BaseScore other = (BaseScore) obj;
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
