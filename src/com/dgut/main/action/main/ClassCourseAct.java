package com.dgut.main.action.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.protocol.HTTP;
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
import com.dgut.main.entity.BClass;
import com.dgut.main.entity.ClassCourse;
import com.dgut.main.entity.Course;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.ClassCourseMng;
import com.dgut.main.manager.ClassMng;
import com.dgut.main.manager.CourseMng;

@Controller
@RequestMapping("classcourse")
public class ClassCourseAct {
	private static final Logger log = LoggerFactory.getLogger(ClassCourseAct.class);
	
	@RequestMapping("v_list.do")
	public String getClass(HttpServletRequest request,HttpServletResponse response,ModelMap model,
			Integer pageNo,String queryDepartmentName,String queryMajorName,String queryClassName){
		Pagination pagination = classMng.getPage(queryDepartmentName, queryMajorName, queryClassName, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryDepartmentName", queryDepartmentName);
		model.addAttribute("queryMajorName", queryMajorName);
		model.addAttribute("queryClassName", queryClassName);
		model.addAttribute("pagination", pagination);
		return "classcourse/list";
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
		return "classcourse/show";
	}
	
	@RequestMapping("v_add.do")
	public String addClassCourse(HttpServletRequest request,HttpServletResponse response,ModelMap model ,String id){
		WebErrors errors = validateId(id, request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		List<Course> courseList = courseMng.getList();
		BClass c = classMng.findById(Integer.parseInt(id));
		model.addAttribute("courseList", courseList);
		model.addAttribute("class", c);
		return "classcourse/slist";
	}
	
	@RequestMapping("o_save.do")
	public String ClassCourseAdd(HttpServletRequest request,HttpServletResponse response,ModelMap model,String classId,String tableNames,String term){
		WebErrors errors = validateTerm(term,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		errors = validateId(classId, request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		BClass c = classMng.findById(Integer.parseInt(classId));
		saveClassCourse(c,tableNames,term);
		adminLogMng.operating(request, "cms.class.classcourse.add", "add.classcourse.class.name="+c.getClassName());
		model.addAttribute("message", "global.success");
		return getClassCourse(request,response,model,classId);
	}


	@RequestMapping("o_delete.do")
	public String deleteClassCourse(HttpServletRequest request,HttpServletResponse response,ModelMap model,String term,String classId){
		WebErrors errors = validateTerm(term,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		errors = validateId(classId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		ccMng.deleteByTerm(classId,term);
		adminLogMng.operating(request, "cms.classcourse.delete", "delete.classcourse.classId="+classId + " ,term="+term);
		model.addAttribute("message", "global.success");
		return getClassCourse(request,response,model,classId);
	}
	
	private WebErrors validateTerm(String term, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(term)){
			errors.addErrorCode("error.required", "学期");
			return errors;
		}
		return errors;
	}

	private void saveClassCourse(BClass c, String tableNames,String term) {
		ClassCourse cc = null;
		
		String[] value = tableNames.split(",");
		for(String id : value){
			cc = new ClassCourse();
			cc.setCourse(courseMng.findById(Integer.parseInt(id)));
			cc.setBclass(c);
			cc.setMajor(c.getMajor());
			cc.setTerm(Integer.parseInt(term));
			cc.setIsInput(0);
			ccMng.save(cc);
		}
		
	}
	private WebErrors validateId(String id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(id)){
			errors.addErrorCode("error.required", "班级id");
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
	private AdminLogMng adminLogMng;
}
