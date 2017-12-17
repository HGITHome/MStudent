package com.dgut.main.entity.base;

import java.io.Serializable;
import java.util.List;

import com.dgut.main.entity.Score;

public abstract class BaseTempScore implements Serializable{

	/**
	 * 用来存临时成绩统计
	 */
	private static final long serialVersionUID = 1L;

	private List<Score> list;
	
	private Object totalPoint;
	
	private Object totalGradePoint;

	public List<Score> getList() {
		return list;
	}

	public void setList(List<Score> list) {
		this.list = list;
	}

	public Object getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(Object o) {
		this.totalPoint = o;
	}

	public Object getTotalGradePoint() {
		return totalGradePoint;
	}

	public void setTotalGradePoint(Object totalGradePoint) {
		this.totalGradePoint = totalGradePoint;
	}
	
	
}
