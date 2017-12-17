package com.dgut.main.action.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.dgut.common.page.SimplePage.cpn;

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
import com.dgut.main.entity.Score;
import com.dgut.main.entity.Temp;
import com.dgut.main.entity.TempScore;
import com.dgut.main.manager.ClassMng;
import com.dgut.main.manager.ScoreMng;
import com.dgut.member.entity.StudentInfo;
import com.dgut.member.manager.StudentInfoMng;

@Controller
@RequestMapping("mscore")
public class MScoreAct {
	
	private static final Logger log = LoggerFactory.getLogger(MScoreAct.class);
	
	@RequestMapping("v_list")
	public String getPage(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer pageNo,String queryDepartmentName,String queryMajorName,String queryClassName){
		Pagination pagination = classMng.getPage(queryDepartmentName, queryMajorName, queryClassName, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryDepartmentName", queryDepartmentName);
		model.addAttribute("queryMajorName", queryMajorName);
		model.addAttribute("queryClassName", queryClassName);
		model.addAttribute("pagination", pagination);
		return "mscore/list";
	}
	
	@RequestMapping("s_list.do")
	public String getClassStudents(HttpServletRequest request,HttpServletResponse response,ModelMap model,String classId){
		WebErrors errors = validateId(classId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		BClass c = classMng.findById(Integer.parseInt(classId));
		model.addAttribute("studentList", c.getStudentSet());
		return "mscore/slist";
	}
	
	@RequestMapping("o_show.do")
	public String showScore(HttpServletRequest request,HttpServletResponse response,ModelMap model,String studentId){
		WebErrors errors = validateShow(studentId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		StudentInfo student = studentMng.findById(Integer.parseInt(studentId));
		List<TempScore> scoreList = scoreMng.getListByStudentId(studentId); 
		Object[] o = scoreMng.getSumGradePoint(studentId);
		List<Temp> tempList = scoreMng.getStatistics(studentId);
		model.addAttribute("total", o);
		model.addAttribute("tempList", tempList);
		model.addAttribute("scoreList", scoreList);
		model.addAttribute("student", student);
		return "mscore/show";
	}
	private WebErrors validateShow(String studentId, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(studentId)){
			errors.addErrorCode("error.required", "学生id");
			return errors;
		}
		return errors;
	}

	private WebErrors validateId(String classId, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(classId)){
			errors.addErrorCode("error.required", "班级id");
			return errors;
		}
		return errors;
	}
	@Autowired
	private ClassMng classMng;
	
	@Autowired
	private ScoreMng scoreMng;
	
	@Autowired
	private StudentInfoMng studentMng;
}
