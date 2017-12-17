package com.dgut.main.manager.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.main.dao.ScoreDao;
import com.dgut.main.entity.BClass;
import com.dgut.main.entity.Course;
import com.dgut.main.entity.Score;
import com.dgut.main.entity.Temp;
import com.dgut.main.entity.TempScore;
import com.dgut.main.manager.ClassCourseMng;
import com.dgut.main.manager.ClassMng;
import com.dgut.main.manager.CourseMng;
import com.dgut.main.manager.ScoreMng;
import com.dgut.member.entity.StudentInfo;
import com.dgut.member.manager.StudentInfoMng;

@Service
@Transactional
public class ScoreMngImpl implements ScoreMng {

	@Transactional(readOnly=true)
	public List<Score> getList(Integer studentId, Integer term, Integer classId) {
		
		return dao.getList(studentId, term, classId);
	}
	
	@Transactional(readOnly=true)
	public List<TempScore> getListByStudentId(String studentId){
		List<TempScore> tempList = new ArrayList<TempScore>();
		TempScore temp = null;
		for(int  term = 1 ; term <= 8 ; term ++){
			List<Score> list = dao.getListByStudentId(Integer.parseInt(studentId),term);
			Object[] o= dao.getScoreSum(Integer.parseInt(studentId),term);
			if(list.size() != 0){
				temp = new TempScore();
				temp.setList(list);
				temp.setTotalPoint(o[0]);
				temp.setTotalGradePoint(o[1]);
				tempList.add(temp);
			}
		}
		return tempList;
	}

	@Transactional(readOnly=true)
	public Object[] getSumGradePoint(String studentId){
		return dao.getSumGradePoint(studentId);
	}
	
	public List<Temp> getStatistics(String studentId){
		DecimalFormat df = new DecimalFormat("0.0");
		Integer[] nums = dao.getcount(Integer.parseInt(studentId));
		List<Temp> temps = new ArrayList<Temp>();
		Temp t = null;
		for(int i = 1 ; i < nums.length ; i ++){
			t = new Temp();
			t.setNumber(nums[i]);
			t.setProportion(df.format(((float)nums[i]/nums[0])*100));
			temps.add(t);
		}
		return temps;
	}
	
	@Transactional(readOnly=true)
	public Score findById(Integer id) {
		
		return dao.findById(id);
	}
	
	@Transactional(readOnly=true)
	public List<Score> getListByClassAndCourse(String courseId, String classId){
		return dao.getListByClassAndCourse(courseId,classId);
	}

	public Score save(String studentId, String courseId, String classId,
			String term, String score){
		Score bean = new Score();
		StudentInfo student = studentMng.findById(Integer.parseInt(studentId));
		Course course = courseMng.findById(Integer.parseInt(courseId));
		BClass c = classMng.findById(Integer.parseInt(classId));
		bean.setStudent(student);
		bean.setCourse(course);
		bean.setBclass(c);
		bean.setTerm(Integer.parseInt(term));
		bean.setScore(Double.parseDouble(score));
		double point = getPoint(Double.parseDouble(score));
		bean.setPoint(point);
		bean.setGrade_point(course.getCreditHour()*point);
		save(bean);
		return bean;
	}
	
	
	public List<Score> savScore(String studentIds, String courseId, String classId, String scores, String term){
		List<Score> scoreList = new ArrayList<Score>();
		String[] ids = studentIds.split(",");
		String[] s = scores.split(",");
		Course course = courseMng.findById(Integer.parseInt(courseId));
		BClass c = classMng.findById(Integer.parseInt(classId));
		for(int i = 0 ; i < ids.length ; i ++){
			Score bean = new Score();
			bean.setStudent(studentMng.findById(Integer.parseInt(ids[i])));
			bean.setCourse(course);
			bean.setTerm(Integer.parseInt(term));
			bean.setScore(Double.parseDouble(s[i]));
			bean.setPoint(getPoint(Double.parseDouble(s[i])));
			bean.setGrade_point(getPoint(Double.parseDouble(s[i]))*course.getCreditHour());
			bean.setBclass(c);
			save(bean);
			scoreList.add(bean);
		}
		return scoreList;
	}

	public Score save(Score bean) {
		
		return dao.save(bean);
	}

	
	public Score update(Score bean) {
		Updater<Score> updater = new Updater<Score>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public Score deleteById(Integer id) {
		
		return dao.deleteById(id);
	}
	
	private double getPoint(double score) {
		StringBuffer sb = new StringBuffer();
		if(score < 60){
			sb.append("0");
		}else{
			if(score >= 60 && score < 70){
				 sb.append(1+"."+(int)score%10);
			}else if(score>=70 && score < 80){
				sb.append(2+"."+(int)score%10);
			}else if(score>=80 && score <90){
				sb.append(3+"."+(int)score%10);
			}else{
				sb.append(4+"."+(int)score%10);
			}
		}
		return Double.parseDouble(sb.toString());
	}

	@Autowired
	private ScoreDao dao;
	
	@Autowired
	private ClassMng classMng;
	
	@Autowired
	private ClassCourseMng ccMng;
	
	@Autowired
	private CourseMng courseMng; 
	
	@Autowired
	private StudentInfoMng studentMng;
}
