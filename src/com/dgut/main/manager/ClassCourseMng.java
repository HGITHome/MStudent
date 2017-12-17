package com.dgut.main.manager;

import java.util.List;




import com.dgut.main.entity.ClassCourse;

public interface ClassCourseMng {

    public List<ClassCourse> getList();
	
	public List<List<ClassCourse>> getListByClassId(Integer classId);
	
	public ClassCourse findById(Integer id);
	
	public ClassCourse save(ClassCourse bean);
	
	public ClassCourse update(ClassCourse bean);
	
	public ClassCourse deleteById(Integer id);

	public ClassCourse[] deleteByTerm(String classId, String term);

	public ClassCourse findByCourseAndClass(String courseId, String classId);
}
