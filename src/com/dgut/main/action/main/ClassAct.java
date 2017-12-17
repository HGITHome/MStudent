package com.dgut.main.action.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.dgut.common.page.SimplePage.cpn;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.CookieUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.main.entity.Admin;
import com.dgut.main.entity.BClass;
import com.dgut.main.entity.Department;
import com.dgut.main.entity.Major;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.ClassMng;
import com.dgut.main.manager.DepartmentMng;
import com.dgut.main.manager.MajorMng;
import com.dgut.main.web.CmsUtils;
import com.dgut.member.entity.Member;
import com.dgut.member.manager.MemberLogMng;
import com.dgut.member.manager.MemberMng;

@Controller
@RequestMapping("class")
public class ClassAct {

	@RequestMapping("v_list.do")
	public String getPage(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer pageNo,String queryClassName){
		Pagination pagination = classMng.getPage(null,null,queryClassName, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryClassName", queryClassName);
		model.addAttribute("pagination", pagination);
		return "class/list";
	}
	
	@RequestMapping("v_add.do")
	public String classAdd(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		List<Department> departmentList = departmentMng.getList();
		List<Major> majorList = majorMng.getList();
		model.addAttribute("majorList", majorList);
		model.addAttribute("departmentList", departmentList);
		return "class/add";
	}
	
	@RequestMapping(value="/ajaxDepartment.do" , method=RequestMethod.POST)
	public void ajaxDepartment(Integer id,HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception, IOException{
		String data = null;
		if(id != null){
		  Department department= departmentMng.findById(id);
		  data  = departmentMng.getDepartmentString(new ArrayList<Major>(department.getMajorSet()));
		}else{
			data = departmentMng.getDepartmentString(majorMng.getList());
		}
		response.getOutputStream().write(data.getBytes("UTF-8"));
	}

	@RequestMapping("o_save.do")
	public String save(HttpServletRequest request,HttpServletResponse response,ModelMap model,BClass bClass){
		Admin admin = CmsUtils.getAdmin(request);
        String departmentId = request.getParameter("departmentList");
        String majorId = request.getParameter("majorList");
        WebErrors errors = validateSave(departmentId,majorId,request);
        if(errors.hasErrors()){
        	return errors.showErrorPage(model);
        }
        bClass.setMajor(majorMng.findById(Integer.parseInt(majorId)));
        bClass.setRegister_time(new Date());
        bClass.setAdmin(admin);
        classMng.save(bClass);
        adminLogMng.operating(request, "cms.class.add", "add.class.name="+bClass.getClassName());
		return "redirect:v_list.do";
	}
	
	@RequestMapping("o_info.do")
	public String infoClass(HttpServletRequest request,HttpServletRequest response,ModelMap model,String id){
		WebErrors errors = validateId(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		BClass c = classMng.findById(Integer.parseInt(id));
		model.addAttribute("class", c);
		return "class/info";
	}
	
	@RequestMapping("o_edit.do")
	public String aditClass(HttpServletRequest request,HttpServletResponse response,ModelMap model,String id){
		WebErrors errors = validateId(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		BClass c = classMng.findById(Integer.parseInt(id));
		List<Department> departmentList = departmentMng.getList();
		List<Major> majorList = majorMng.getList();
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("majorList", majorList);
		model.addAttribute("class", c);
		return "class/edit";
	}
	
	@RequestMapping("o_update.do")
	public String update(HttpServletRequest request,HttpServletResponse response,ModelMap model,BClass bean){
		Admin admin = CmsUtils.getAdmin(request);
		String majorId = request.getParameter("majorList");
		WebErrors errors = validateId(majorId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		bean.setMajor(majorMng.findById(Integer.parseInt(majorId)));
		bean.setLastUpdateTime(new Date());
		bean.setAdmin(admin);
		classMng.update(bean);
		adminLogMng.operating(request, "cms.update.class", "update.class.id="+bean.getId());
		return "redirect:v_list.do";
	}
	
	@RequestMapping("o_delete.do")
	public String delete(HttpServletRequest request,HttpServletResponse response,ModelMap model,String id){
		WebErrors errors = validateId(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		classMng.deleteById(Integer.parseInt(id));
		adminLogMng.operating(request, "cms.class.delete", "delete.class.id="+id);
		return "redirect:v_list.do";
	}
	
	private WebErrors validateId(String id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(id)){
			errors.addErrorCode("error.required", "id");
			return errors;
		}
		return errors;
	}

	private WebErrors validateSave(String departmentId, String majorId,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(departmentId)){
			errors.addErrorCode("error.required", "院系id");
			return errors;
		}
		if(StringUtils.isBlank(majorId)){
			errors.addErrorCode("error.required", "专业id");
			return errors;
		}
		return errors;
	}
	@Autowired
	private ClassMng classMng;
	
	@Autowired
	private MajorMng majorMng;
	
	@Autowired
	private DepartmentMng departmentMng;
	
	@Autowired
	private MemberMng memberMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
}
