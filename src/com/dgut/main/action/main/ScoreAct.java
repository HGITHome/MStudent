package com.dgut.main.action.main;

import static com.dgut.common.page.SimplePage.cpn;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.CookieUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.main.entity.BClass;
import com.dgut.main.entity.ClassCourse;
import com.dgut.main.entity.Course;
import com.dgut.main.entity.Score;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.ClassCourseMng;
import com.dgut.main.manager.ClassMng;
import com.dgut.main.manager.CourseMng;
import com.dgut.main.manager.ScoreMng;
import com.dgut.member.entity.StudentInfo;
import com.dgut.member.manager.StudentInfoMng;

@Controller
@RequestMapping("score")
public class ScoreAct {

	private static final Logger log = LoggerFactory.getLogger(ScoreAct.class);
	
	@RequestMapping("v_list.do")
	public String getClass(HttpServletRequest request,HttpServletResponse response,ModelMap model,
			Integer pageNo,String queryDepartmentName,String queryMajorName,String queryClassName){
		Pagination pagination = classMng.getPage(queryDepartmentName, queryMajorName, queryClassName, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryDepartmentName", queryDepartmentName);
		model.addAttribute("queryMajorName", queryMajorName);
		model.addAttribute("queryClassName", queryClassName);
		model.addAttribute("pagination", pagination);
		return "score/list";
	}
	
	
	@RequestMapping("o_show.do")
	public String getClassCourse(HttpServletRequest request,HttpServletResponse rsponse,ModelMap model,String id){
		WebErrors errors = validateId(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		BClass c = classMng.findById(Integer.parseInt(id));
		List<List<ClassCourse>> cclist = ccMng.getListByClassId(Integer.parseInt(id));
		model.addAttribute("cclist", cclist);
		model.addAttribute("class", c);
		return "score/show";
	}
	
	
	@RequestMapping("o_input.do")
	public String scoreInput(HttpServletRequest request,HttpServletResponse response,ModelMap model,String courseId,String classId){
		WebErrors errors = validateInput(courseId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		errors = validateId(classId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		ClassCourse cc = ccMng.findByCourseAndClass(courseId,classId);
		List<StudentInfo> studentList = studentMng.getListByClassId(Integer.parseInt(classId));
		model.addAttribute("cc", cc);
		model.addAttribute("studentList", studentList);
		return "score/input";
	}
	
	private WebErrors validateInput(String courseId, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(courseId)){
			errors.addErrorString("课程id参数不能为空！");
			return errors;
		}
		return errors;
	}

	@RequestMapping("o_save.do")
	public String saveScore(HttpServletRequest request,HttpServletResponse response,ModelMap model,String studentIds,String courseId,String classId,String scores,String term,String cid){
		WebErrors errors = validateParameter(studentIds,courseId,classId,term,cid,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		scoreMng.savScore(studentIds,courseId,classId,scores,term);
		ClassCourse cc = ccMng.findById(Integer.parseInt(cid));
		cc.setIsInput(1);
		ccMng.update(cc);
		adminLogMng.operating(request, "cms.score.save", "save.score.class.id="+classId +",course.id"+courseId);
		return "redirect:o_show.do?id="+classId;
	}

	
	@RequestMapping("o_check.do")
	public String scoreCheck(HttpServletRequest request,HttpServletResponse response,ModelMap model,String courseId,String classId){
		WebErrors errors = validateInput(courseId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		errors = validateId(classId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		List<Score> scoreList = scoreMng.getListByClassAndCourse(courseId,classId);
		ClassCourse cc = ccMng.findByCourseAndClass(courseId,classId);
		model.addAttribute("cc",cc);
		model.addAttribute("scoreList", scoreList);
		return "score/check";
	}
	
	private WebErrors validateParameter(String studentId, String courseId,String classId, String term,String cid, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(studentId) || StringUtils.isBlank(courseId)||StringUtils.isBlank(classId) || StringUtils.isBlank(term) || StringUtils.isBlank(cid)){
			errors.addErrorString("录入失败！参数不完整!");
			return errors;
		}
		return errors;
	}


	private WebErrors validateId(String id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(id)){
			errors.addErrorCode("error.required", "班级id参数");
			return errors;
		}
		return errors;
	}

	@Autowired
	private ClassMng classMng;
	
	@Autowired
	private ClassCourseMng ccMng;
	
	@Autowired
	private CourseMng courseMng; 
	
	@Autowired
	private StudentInfoMng studentMng;
	
	@Autowired
	private ScoreMng scoreMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
}
