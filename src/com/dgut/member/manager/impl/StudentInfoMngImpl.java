package com.dgut.member.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.member.dao.StudentInfoDao;
import com.dgut.member.entity.StudentInfo;
import com.dgut.member.manager.StudentInfoMng;

@Service
@Transactional
public class StudentInfoMngImpl implements StudentInfoMng {

	@Transactional(readOnly=true)
	public Pagination getPage(String queryMajorName,String queryStudentName,
			String queryStudentNumber, Integer pageNo, Integer pageSize) {
		
		return dao.getPage(queryMajorName,queryStudentName,queryStudentNumber,pageNo, pageSize);
	}

	@Transactional(readOnly=true)
	public StudentInfo findById(Integer id) {
		
		return dao.findById(id);
	}

	@Transactional(readOnly=true)
	public StudentInfo findBySchoolNumber(String schoolNumber) {
		
		return dao.findBySchoolNumber(schoolNumber);
	}

	@Transactional(readOnly=true)
	public List<StudentInfo> getListByClassId(Integer classId){
		return dao.getListByClassId(classId);
	}
	
	public StudentInfo save(StudentInfo bean) {
		
		return dao.save(bean);
	}

	
	public StudentInfo update(StudentInfo bean) {
		Updater<StudentInfo> updater = new Updater<StudentInfo>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public StudentInfo deleteById(Integer id) {
		
		return dao.deleteById(id);
	}

	
	public StudentInfo[] deleteByIds(Integer[] ids) {
		
		return dao.deleteByIds(ids);
	}
	
	@Autowired
	private StudentInfoDao dao;

}
