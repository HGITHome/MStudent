package com.dgut.member.entity.base;

import java.io.Serializable;
import java.util.Date;

import com.dgut.main.entity.BClass;
import com.dgut.main.entity.Major;
import com.dgut.member.entity.Member;

public class BaseStudentInfo implements Serializable {

	/**
	 * 学生信息类
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private String name;
	
	private String schoolNumber;
	
	private Integer gender;
	
	private Major major;
	
	private BClass bclass;
	
	private String nation;//民族
	
	private String political_status;//政治面貌
	
	private String nickname;//曾用名
	
	private String ID;
	
	private Date birthday;
	
	private String Native;//籍贯
	
	private String education;
	
	private Date entranceTime;
	
	private String address;
	
	private String homePhone;
	
	private String  postalcode;//邮政编码
	
	private String selfPhone;
	
	private String email;
	
	private String iconUrl;
	
	private String miniUrl;
	
	private Member member;

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

	public String getSchoolNumber() {
		return schoolNumber;
	}

	public void setSchoolNumber(String schoolNumber) {
		this.schoolNumber = schoolNumber;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
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

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPolitical_status() {
		return political_status;
	}

	public void setPolitical_status(String political_status) {
		this.political_status = political_status;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getNative() {
		return Native;
	}

	public void setNative(String native1) {
		Native = native1;
	}

	
	
	
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public Date getEntranceTime() {
		return entranceTime;
	}

	public void setEntranceTime(Date entranceTime) {
		this.entranceTime = entranceTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getSelfPhone() {
		return selfPhone;
	}

	public void setSelfPhone(String selfPhone) {
		this.selfPhone = selfPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getMiniUrl() {
		return miniUrl;
	}

	public void setMiniUrl(String miniUrl) {
		this.miniUrl = miniUrl;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	
	
	

}
