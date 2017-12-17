package com.dgut.main.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.dgut.common.page.SimplePage.cpn;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dgut.common.page.Pagination;
import com.dgut.common.upload.FileUtils;
import com.dgut.common.web.CookieUtils;
import com.dgut.main.entity.Notice;
import com.dgut.main.entity.UploadFile;
import com.dgut.main.manager.FileMng;
import com.dgut.main.manager.NoticeMng;
import com.dgut.main.web.FrontUtils;
import com.dgut.main.web.WebErrors;

@Controller
public class WelcomeAct {
	@RequestMapping("/index.do")
	public String index(HttpServletRequest request, ModelMap model){
		Pagination pagination = noticeMng.getPage(null, null, cpn(0), CookieUtils.getPageSize(request));
		Pagination pagination2 = fileMng.getPage(null, 0, CookieUtils.getPageSize(request));
		@SuppressWarnings("unchecked")
		List<Notice> lists = (List<Notice>) pagination.getList();
		@SuppressWarnings("unchecked")
		List<UploadFile> fileList = (List<UploadFile>) pagination2.getList();
		if(lists.size() > 5){
			lists.subList(0, 5);
		}
		if(fileList.size() > 5){
			fileList = fileList.subList(0, 5);
		}
		model.addAttribute("noticeList", lists);
		model.addAttribute("fileList", fileList);
		FrontUtils.adminData(request, model);
		return "index";
	}
	
	@RequestMapping("/map.do")
	public String map() {
		return "map";
	}

	@RequestMapping("/top.do")
	public String top(HttpServletRequest request, ModelMap model) {
		FrontUtils.adminData(request, model);
		return "top";
	}

	@RequestMapping("/main.do")
	public String main() {
		return "main";
	}

	@RequestMapping("/left.do")
	public String left() {
		return "left";
	}

	@RequestMapping("/right.do")
	public String right(HttpServletRequest request, ModelMap model) {
		Pagination pagination = noticeMng.getPage(null, null, cpn(0), CookieUtils.getPageSize(request));
		Pagination pagination2 = fileMng.getPage(null, 0, CookieUtils.getPageSize(request));
		@SuppressWarnings("unchecked")
		List<Notice> lists = (List<Notice>) pagination.getList();
		@SuppressWarnings("unchecked")
		List<UploadFile> fileList = (List<UploadFile>) pagination2.getList();
		if(lists.size() > 5){
			lists = lists.subList(0, 5);
		}
		if(fileList.size() > 5){
			fileList = fileList.subList(0, 5);
		}
		model.addAttribute("noticeList", lists);
		model.addAttribute("fileList", fileList);
		FrontUtils.adminData(request, model);
		return "right";
	}
	
	@RequestMapping("o_info.do")
	public String info(HttpServletRequest request,HttpServletResponse response,ModelMap model,String id){
		WebErrors errors = validate(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		Notice notice = noticeMng.findById(Integer.parseInt(id));
		String content = fileUtils.readFile(notice.getFileUrl());
		model.addAttribute("notice", notice);
		model.addAttribute("content", content);
		return "info";
	}
	
	
	@RequestMapping("v_list.do")
	public String getNoticePage(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryTitle,String queryUsername,Integer pageNo){
		Pagination pagination = noticeMng.getPage(queryTitle, queryUsername, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryTitle", queryTitle);
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("pagination", pagination);
		return "list";
		
	}
	
	@RequestMapping("o_list.do")
	public String getFilePage(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryFileName,Integer pageNo){
		Pagination pagination = fileMng.getPage(queryFileName, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryFileName", queryFileName);
		model.addAttribute("pagination", pagination);
		return "flist";
	}
	
	private WebErrors validate(String id, HttpServletRequest request) {
	    WebErrors errors = WebErrors.create(request);
	    if(StringUtils.isBlank(id) || id.trim().equals("")){
	    	errors.addErrorCode("error.required", "公告id");
	    	return errors;
	    }
		return errors;
	}

	@Autowired
	private NoticeMng noticeMng;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private FileMng fileMng;
}
