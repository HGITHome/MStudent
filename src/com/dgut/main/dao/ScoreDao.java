package com.dgut.main.dao;

import java.util.List;

import com.dgut.common.hibernate3.Updater;
import com.dgut.main.entity.Score;

public interface ScoreDao {
	
	public List<Score> getList(Integer id,Integer term,Integer classId);
	
	public Score findById(Integer id);
	
	public Score save(Score bean);
	
	public Score updateByUpdater(Updater<Score> updater);
	
	public Score deleteById(Integer id);

	public List<Score> getListByClassAndCourse(String courseId, String classId);

	public List<Score> getListByStudentId(Integer parseInt, int term);

	public Object[] getScoreSum(Integer studentId, Integer term);

	public Object[] getSumGradePoint(String studentId);

	public Integer[] getcount(Integer studentId);
	
}
