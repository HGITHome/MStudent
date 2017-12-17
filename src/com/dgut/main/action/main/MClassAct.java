package com.dgut.main.action.main;

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

import static com.dgut.common.page.SimplePage.cpn;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.CookieUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.main.manager.ClassMng;
import com.dgut.member.entity.StudentInfo;
import com.dgut.member.manager.StudentInfoMng;

@Controller
@RequestMapping("mclass")
public class MClassAct {
	private static final Logger log = LoggerFactory.getLogger(MClassAct.class);
	
	@RequestMapping("v_list.do")
	public String getPage(HttpServletRequest request,HttpServletResponse response,ModelMap model,
			Integer pageNo,String queryDepartmentName,String queryMajorName,String queryClassName){
		Pagination pagination = classMng.getPage(queryDepartmentName,queryMajorName,queryClassName, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryDepartmentName", queryDepartmentName);
		model.addAttribute("queryMajorName", queryMajorName);
		model.addAttribute("queryClassName", queryClassName);
		model.addAttribute("pagination", pagination);
		return "mclass/list";
	}
	
	@RequestMapping("o_info.do")
	public String getStudentPage(HttpServletRequest request,HttpServletResponse response,ModelMap model,String classId){
		WebErrors errors = validateId(classId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		List<StudentInfo> studentList = studentMng.getListByClassId(Integer.parseInt(classId));
		model.addAttribute("studentList", studentList);
		log.info("cms.class.student.info");
		return "mclass/slist";
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
	private StudentInfoMng studentMng;
}
