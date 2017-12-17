package com.dgut.main.entity.base;

import java.io.Serializable;

public abstract class BaseTemp implements Serializable {

	/**
	 * 临时统计类
	 */
	private static final long serialVersionUID = 1L;

	private Integer number;
	private String proportion;
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getProportion() {
		return proportion;
	}
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	
	
}
