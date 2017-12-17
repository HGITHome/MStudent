package com.dgut.main.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.main.dao.ClassCourseDao;
import com.dgut.main.entity.BClass;
import com.dgut.main.entity.ClassCourse;
import com.dgut.main.manager.ClassCourseMng;
import com.dgut.main.manager.ClassMng;

@Service
@Transactional
public class ClassCourseMngImpl implements ClassCourseMng {

	 @Transactional(readOnly=true)
	public List<ClassCourse> getList() {
	
		return dao.getList();
	}

	@Transactional(readOnly=true)
	public List<List<ClassCourse>> getListByClassId(Integer classId) {
	    BClass bean = classMng.findById(classId);
	    List<List<ClassCourse>> list = new ArrayList<List<ClassCourse>>();
	    for(int i = 1 ; i <= 8 ; i ++){
	    	List<ClassCourse> l = dao.getListByClassBean(bean,i);
	    	if(l.size() != 0){
	    		list.add(l);
	    	}
	    }
		return list;
	}

	@Transactional(readOnly=true)
	public ClassCourse findById(Integer id) {
		
		return dao.findById(id);
	}

	@Transactional(readOnly=true)
	public ClassCourse findByCourseAndClass(String courseId, String classId){
		return dao.findByCourseAndClass(courseId,classId);
	}
	
	public ClassCourse save(ClassCourse bean) {
		
		return dao.save(bean);
	}

	
	public ClassCourse update(ClassCourse bean) {
		Updater<ClassCourse> updater = new Updater<ClassCourse>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public ClassCourse deleteById(Integer id) {
		
		return dao.deleteById(id);
	}

	
	public ClassCourse[] deleteByTerm(String classId, String term) {
		List<ClassCourse> list = dao.getListByClassIdAndTerm(Integer.parseInt(classId), Integer.parseInt(term));
		ClassCourse[] classcourse = new ClassCourse[list.size()];
		for(int i = 0 ; i < list.size() ; i ++){
			classcourse[i] = dao.delete(list.get(i));
		}
		return classcourse;
	}
	
	@Autowired
	private ClassCourseDao dao;
	
	@Autowired
	private ClassMng classMng;


}
