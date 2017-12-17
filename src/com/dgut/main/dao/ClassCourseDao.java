package com.dgut.main.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.main.entity.BClass;
import com.dgut.main.entity.ClassCourse;

public interface ClassCourseDao {
	
	public List<ClassCourse> getList();
	
	public List<ClassCourse> getListByClassIdAndTerm(Integer classId,Integer term);
	
	public List<ClassCourse> getListByClassBean(BClass bean,Integer term);
	
	public ClassCourse findById(Integer id);
	
	public ClassCourse save(ClassCourse bean);
	
	public ClassCourse updateByUpdater(Updater<ClassCourse> updater);
	
	public ClassCourse deleteById(Integer id);

	public ClassCourse delete(ClassCourse bean);

	public ClassCourse findByCourseAndClass(String courseId, String classId);
}
