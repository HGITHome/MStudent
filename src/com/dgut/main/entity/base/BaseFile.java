package com.dgut.main.entity.base;

import java.io.Serializable;
import java.util.Date;

import com.dgut.main.entity.Admin;

public abstract class BaseFile implements Serializable {

	/**
	 * 文件类
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String fileName;
	private String filePath;
	private Date uploadTime;
	private Integer downNumber;
	private Admin admin;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	
	public Integer getDownNumber() {
		return downNumber;
	}
	public void setDownNumber(Integer downNumber) {
		this.downNumber = downNumber;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	

}
