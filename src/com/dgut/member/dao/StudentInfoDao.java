package com.dgut.member.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.member.entity.StudentInfo;

public interface StudentInfoDao {
	
	public Pagination getPage(String queryMajorName,String queryStudentName,String queryStudentNumber,Integer pageNo,Integer pageSize);
	
	public StudentInfo findById(Integer id);
	
	public StudentInfo findBySchoolNumber(String schoolNumber);
	 
	public StudentInfo save(StudentInfo bean);
	
	public StudentInfo updateByUpdater(Updater<StudentInfo> updater);
	
	public StudentInfo deleteById(Integer id);
	
	public StudentInfo[] deleteByIds(Integer[] ids);

	public List<StudentInfo> getListByClassId(Integer classId);
}
