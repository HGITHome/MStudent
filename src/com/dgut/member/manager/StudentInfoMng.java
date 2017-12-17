package com.dgut.member.manager;


import java.util.List;

import com.dgut.common.page.Pagination;
import com.dgut.member.entity.StudentInfo;

public interface StudentInfoMng {

    public Pagination getPage(String queryMajorName,String queryStudentName,String queryStudentNumber,Integer pageNo,Integer pageSize);
	
	public StudentInfo findById(Integer id);
	
	public StudentInfo findBySchoolNumber(String schoolNumber);
	
	public List<StudentInfo> getListByClassId(Integer classId);
	
	public StudentInfo save(StudentInfo bean);
	
	public StudentInfo update(StudentInfo bean);
	
	public StudentInfo deleteById(Integer id);
	
	public StudentInfo[] deleteByIds(Integer[] ids);
}
