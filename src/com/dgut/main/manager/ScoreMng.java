package com.dgut.main.manager;

import java.util.List;









import com.dgut.main.entity.Score;
import com.dgut.main.entity.Temp;
import com.dgut.main.entity.TempScore;

public interface ScoreMng {

    public List<Score> getList(Integer id,Integer term,Integer classId);
	
	public Score findById(Integer id);
	
	public Score save(Score bean);
	
	public Score update(Score bean);
	
	public Score deleteById(Integer id);

	public Score save(String studentId, String courseId, String classId,
			String term, String score);

	public List<Score> savScore(String studentIds, String courseId, String classId, String scores, String term);

	public List<Score> getListByClassAndCourse(String courseId, String classId);

	public List<TempScore> getListByStudentId(String studentId);

	public Object[] getSumGradePoint(String studentId);

	public List<Temp> getStatistics(String studentId);
}
