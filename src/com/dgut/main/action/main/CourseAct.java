package com.dgut.main.action.main;

import static com.dgut.common.page.SimplePage.cpn;

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
import com.dgut.main.entity.Course;
import com.dgut.main.entity.CourseCategory;
import com.dgut.main.entity.Department;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.CourseCategoryMng;
import com.dgut.main.manager.CourseMng;
import com.dgut.main.manager.DepartmentMng;

@Controller
@RequestMapping("course")
public class CourseAct {
	private static final Logger log = LoggerFactory.getLogger(CourseAct.class);
	
	@RequestMapping("v_list.do")
	public String getPage(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryCourseName,Integer pageNo){
		Pagination pagination = courseMng.getPage(queryCourseName, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryCourseName", queryCourseName);
		model.addAttribute("pagination", pagination);
		return "course/list";
	}
	
	@RequestMapping("v_add.do")
	public String courseAdd(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		List<Department> departmentList = departmentMng.getList();
		List<CourseCategory> courseCategoryList = courseCategoryMng.getList();
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("courseCategoryList", courseCategoryList);
		return "course/add";
	}
	
	@RequestMapping("o_save.do")
	public String courseSave(HttpServletRequest request,HttpServletResponse response,ModelMap model,Course course){
		String departmentId = request.getParameter("departmentList");
		String courseCategoryId = request.getParameter("courseCategoryId");
		WebErrors errors = validateSave(departmentId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		errors = validateCourseCategoryId(courseCategoryId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		course.setDepartment(departmentMng.findById(Integer.parseInt(departmentId)));
		course.setCourseCategory(courseCategoryMng.findById(Integer.parseInt(courseCategoryId)));
		courseMng.save(course);
		adminLogMng.operating(request, "cms.course.add", "add.course.name="+course.getCourseName());
		log.info("cms.save.course");
		return "redirect:v_list.do";
	}
	
	
	

	@RequestMapping("o_edit")
	public String courseEdit(HttpServletRequest request,HttpServletResponse response,ModelMap model,String id){
		WebErrors errors = validateId(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		Course course = courseMng.findById(Integer.parseInt(id));
		List<Department> departmentList = departmentMng.getList();
		List<CourseCategory> courseCategoryList = courseCategoryMng.getList();
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("courseCategoryList", courseCategoryList);
		model.addAttribute("course", course);
		return "course/edit";
	}
	
	@RequestMapping("o_update.do")
	public String courseUpdate(HttpServletRequest request,HttpServletResponse response,ModelMap model,Course course){
		System.out.println(course.getCreditHour());
		String courseCategoryId = request.getParameter("courseCategoryId");
		course.setCourseCategory(courseCategoryMng.findById(Integer.parseInt(courseCategoryId)));
		courseMng.update(course);
		adminLogMng.operating(request, "cms.course.update", "update.course.id="+course.getId());
		log.info("update.course");
		return "redirect:v_list.do";
	}

	@RequestMapping("o_delete.do")
	public String courseDelete(HttpServletRequest request,HttpServletResponse response,ModelMap model,String id){
		WebErrors errors = validateId(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		courseMng.deleteById(Integer.parseInt(id));
		adminLogMng.operating(request, "cms.course.delete", "delete.course.id="+id);
		return "redirect:v_list.do";
	}
	
	private WebErrors validateSave(String departmentId,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(departmentId)){
			errors.addErrorCode("error.required", "开课单位");
			return errors;
		}
		return errors;
	}
	
	private WebErrors validateId(String id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(id)){
			errors.addErrorCode("error.required", "课程id");
			return errors;
		}
		return errors;
	}
	
	private WebErrors validateCourseCategoryId(String courseCategoryId,HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(courseCategoryId)){
			errors.addErrorCode("erros.required", "课程类别id");
			return errors;
		}
		return errors;
	}
	@Autowired
	private CourseMng courseMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
	
	@Autowired
	private DepartmentMng departmentMng;
	
	@Autowired
	private CourseCategoryMng courseCategoryMng;
}
