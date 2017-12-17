package com.dgut.main.action;

import static com.dgut.common.page.SimplePage.cpn;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.page.Pagination;
import com.dgut.common.web.CookieUtils;
import com.dgut.main.entity.AdminLog;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.web.WebErrors;

@Controller
@RequestMapping("/log")
public class AdminLogAct {
	@RequestMapping("/v_list_operating.do")
	public String operated(HttpServletRequest request,HttpServletResponse response,Model model,String queryUsername,String queryTitle,String queryIp,Integer pageNo){
		
		Pagination pagination = adminLogMng.getPage(AdminLog.OPERATING, queryUsername, queryTitle, queryIp, cpn(pageNo),CookieUtils
				.getPageSize(request));
		model.addAttribute("pageNo", pagination.getPageNo());
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryTitle", queryTitle);
		model.addAttribute("queryIp", queryIp);
		model.addAttribute("pagination", pagination);
		return "log/list_operating";
	}
	
	@RequestMapping("/o_delete_operating.do")
	public String LogOperatingDelete(HttpServletRequest request,HttpServletResponse response,String ids,Integer pageNo,String queryUsername,String queryTitle,String queryIp,ModelMap model){
		WebErrors errors = validateIds(ids,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		adminLogMng.deleteById(Integer.parseInt(ids));
		Pagination pagination = adminLogMng.getPage(AdminLog.OPERATING, queryUsername, queryTitle, queryIp, cpn(pageNo),CookieUtils
				.getPageSize(request));
		model.addAttribute("pageNo", pagination.getPageNo());
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryTitle", queryTitle);
		model.addAttribute("queryIp", queryIp);
		model.addAttribute("pagination", pagination);
		return "log/list_operating";
	}
	
	
	@RequestMapping("/v_list_login_success.do")
	public String logSuccess(HttpServletRequest request,HttpServletResponse response,String queryUsername,String queryTitle,String queryIp,Integer pageNo,Model model){
		Pagination pagination = adminLogMng.getPage(AdminLog.LOGIN_SUCCESS, queryUsername, queryTitle, queryIp, cpn(pageNo),CookieUtils
				.getPageSize(request));
		model.addAttribute("pageNo", pagination.getPageNo());
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryTitle", queryTitle);
		model.addAttribute("queryIp", queryIp);
		model.addAttribute("pagination", pagination);
		return "log/list_login_success";
	}
	
	@RequestMapping("/o_delete_login_success.do")
	public String LogSuccessDelete(HttpServletRequest request,HttpServletResponse response,String ids,Integer pageNo,String queryUsername,String queryTitle,String queryIp,ModelMap model){
		WebErrors errors = validateIds(ids,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		adminLogMng.deleteById(Integer.parseInt(ids));
		Pagination pagination = adminLogMng.getPage(AdminLog.LOGIN_SUCCESS, queryUsername, queryTitle, queryIp, cpn(pageNo),CookieUtils
				.getPageSize(request));
		model.addAttribute("pageNo", pagination.getPageNo());
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryTitle", queryTitle);
		model.addAttribute("queryIp", queryIp);
		model.addAttribute("pagination", pagination);
		return "log/list_login_success";
	}
	
	
	@RequestMapping("/v_list_login_failure.do")
	public String logFailure(HttpServletRequest request,HttpServletResponse response,String queryUsername,String queryTitle,String queryIp,Integer pageNo,Model model){
		
		Pagination pagination = adminLogMng.getPage(AdminLog.LOGIN_FAILURE, queryUsername, queryTitle, queryIp, cpn(pageNo),CookieUtils
				.getPageSize(request));
		model.addAttribute("pageNo", pagination.getPageNo());
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryTitle", queryTitle);
		model.addAttribute("queryIp", queryIp);
		model.addAttribute("pagination", pagination);
		
		return "log/list_login_failure";
	}
	
	@RequestMapping("/o_delete_login_failure.do")
	public String LogFailureDelete(HttpServletRequest request,HttpServletResponse response,String ids,Integer pageNo,String queryUsername,String queryTitle,String queryIp,ModelMap model){
		WebErrors errors = validateIds(ids,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		adminLogMng.deleteById(Integer.parseInt(ids));
		Pagination pagination = adminLogMng.getPage(AdminLog.LOGIN_FAILURE, queryUsername, queryTitle, queryIp, cpn(pageNo),CookieUtils
				.getPageSize(request));
		model.addAttribute("pageNo", pagination.getPageNo());
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryTitle", queryTitle);
		model.addAttribute("queryIp", queryIp);
		model.addAttribute("pagination", pagination);
		return "log/list_login_failure";
	}

	private WebErrors validateIds(String ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(ids)){
			errors.addErrorCode("error.request", "日志记录id");
			return errors;
		}
		return errors;
	}

	@Autowired
	private AdminLogMng adminLogMng;
}
