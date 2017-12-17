package com.dgut.main.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dgut.common.hibernate3.Finder;
import com.dgut.common.hibernate3.HibernateBaseDao;
import com.dgut.main.dao.ScoreDao;
import com.dgut.main.entity.Score;

@Repository
public class ScoreDaoImpl extends HibernateBaseDao<Score, Integer> implements ScoreDao {

	
	public List<Score> getList(Integer studentId, Integer term, Integer classId) {
		String hql = "select bean from Score bean where bean.student.id =:studentId  and bean.term=:term and bean.bclass.id=:classId";
		List<Score> list =getSession().createQuery(hql).setParameter("studentId", studentId).setParameter("term", term).setParameter("classId", classId).list();
		return list;
	}

	
	public Score findById(Integer id) {
		Score bean = super.get(id);
		return bean;
	}

	
	public List<Score> getListByStudentId(Integer studentId, int term){
		String hql = "select bean from Score bean where bean.student.id=:studentId and bean.term =:term order by bean.id asc";
		List<Score> list = getSession().createQuery(hql).setParameter("studentId", studentId).setParameter("term", term).list();
		return list;
	}
	
	public List<Score> getListByClassAndCourse(String courseId, String classId){
		Finder f = Finder.create("select bean from Score bean where 1=1 and bean.course.id=:courseId and bean.bclass.id=:classId order by bean.student.schoolNumber asc");
		f.setParam("courseId", Integer.parseInt(courseId)).setParam("classId", Integer.parseInt(classId));
		return find(f);
	}
	
	public Object[] getScoreSum(Integer studentId, Integer term){
		String hql = "select sum(bean.point),sum(bean.grade_point) from Score bean where bean.student.id=:studentId and bean.term=:term";
		Object[] o = (Object[]) getSession().createQuery(hql).setParameter("studentId", studentId).setParameter("term", term).list().get(0);
		return o;
	}
	
	
	public Object[] getSumGradePoint(String studentId){
		String hql = "select sum(bean.point),sum(bean.grade_point) from Score bean where bean.student.id=:studentId";
		Object[] o = (Object[]) getSession().createQuery(hql).setParameter("studentId", Integer.parseInt(studentId)).list().get(0);
		return o;
	}
	
	public Integer[] getcount(Integer studentId){
		Integer[] nums = new Integer[6];
		String hql = "select count(*) from Score bean where bean.student.id =:studentId";
		String hql1 = "select count(*) from Score bean where bean.score < 60 and bean.student.id =:studentId";
		String hql2 = "select count(*) from Score bean where bean.score between 60 and 70 and bean.student.id =:studentId";
		String hql3 = "select count(*) from Score bean where bean.score between 70 and 80 and bean.student.id =:studentId";
		String hql4 = "select count(*) from Score bean where bean.score between 80 and 90 and bean.student.id =:studentId";
		String hql5 = "select count(*) from Score bean where bean.score > 90 and bean.student.id =:studentId";
	    int totalCount =  ((Number)getSession().createQuery(hql).setParameter("studentId", studentId).iterate().next()).intValue();
	    int totalCount1 =  ((Number)getSession().createQuery(hql1).setParameter("studentId", studentId).iterate().next()).intValue();
	    int totalCount2 =  ((Number)getSession().createQuery(hql2).setParameter("studentId", studentId).iterate().next()).intValue();
	    int totalCount3 =  ((Number)getSession().createQuery(hql3).setParameter("studentId", studentId).iterate().next()).intValue();
	    int totalCount4 =  ((Number)getSession().createQuery(hql4).setParameter("studentId", studentId).iterate().next()).intValue();
	    int totalCount5 =  ((Number)getSession().createQuery(hql5).setParameter("studentId", studentId).iterate().next()).intValue();
	    nums[0]=totalCount;
	    nums[1]=totalCount1;
	    nums[2]=totalCount2;
	    nums[3]=totalCount3;
	    nums[4]=totalCount4;
	    nums[5]=totalCount5;
	    return nums;
	}
	
	public Score save(Score bean) {
		getSession().save(bean);
		return bean;
	}

	
	public Score deleteById(Integer id) {
		Score bean = super.get(id);
		if(bean != null){
			getSession().delete(bean);
		}
		return bean;
	}

	
	protected Class<Score> getEntityClass() {
		
		return Score.class;
	}

}
