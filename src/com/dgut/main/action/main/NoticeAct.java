package com.dgut.main.action.main;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.dgut.common.page.SimplePage.cpn;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dgut.common.image.ImageScale;
import com.dgut.common.page.Pagination;
import com.dgut.common.upload.FileRepository;
import com.dgut.common.upload.FileUtils;
import com.dgut.common.upload.UploadUtils;
import com.dgut.common.web.CookieUtils;
import com.dgut.core.web.WebErrors;
import com.dgut.main.entity.Notice;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.NoticeMng;

@Controller
@RequestMapping("notice")
public class NoticeAct {

	private static final Logger log = LoggerFactory.getLogger(NoticeAct.class);
	
	@RequestMapping("v_list.do")
	public String getNoticePage(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryTitle,String queryUsername,Integer pageNo){
		Pagination pagination = noticeMng.getPage(queryTitle, queryUsername, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryTitle", queryTitle);
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("pagination", pagination);
		return "notice/list";
		
	}
	
	@RequestMapping("o_info.do")
	public String info(HttpServletRequest request,HttpServletResponse response,ModelMap model,String noticeId){
		WebErrors errors = validateId(noticeId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		Notice notice = noticeMng.findById(Integer.parseInt(noticeId));
		String content = fileUtils.readFile(notice.getFileUrl());
		model.addAttribute("notice", notice);
		model.addAttribute("content", content);
		return "notice/info";
	}
	
	@RequestMapping("o_add.do")
	public String add(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		return "notice/add";
	}
	@RequestMapping("o_save.do")
	public String save(HttpServletRequest request,HttpServletResponse response,ModelMap model,@RequestParam(value = "photo", required = false) MultipartFile photo){
		WebErrors errors = WebErrors.create(request);
		String photoUrl = null,miniUrl=null;
		String origName = photo.getOriginalFilename();
		String extName = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
		if(!"gif,jpg,jpeg,png,bmp".contains(extName)){
			errors.addErrorString("请上传后缀名为:gif,jpg,jpeg,png,bmp的照片");
			return errors.showErrorPage(model);
		}
		try {
			photoUrl=fileRepository.storeByExt("/upload/file", extName, photo);
			ServletContext  context=request.getSession().getServletContext();
			File fi = new File(context.getRealPath(photoUrl)); //大图文件  


			String miniPath=context.getRealPath("/upload/file");
			String miniName=UploadUtils.generateFilename("mini", extName);
			File fo = new File(miniPath,miniName); //将要转换出的小图文件
			miniUrl="/upload/file/"+miniName;
			imageScale.resizeFix(fi, fo,180,180);

		} catch (IOException e) {
			log.info("上传照片时:"+e.getMessage());
			errors.addErrorString("上传相片失败");
			return errors.showErrorPage(model);
		} catch (Exception e) {
			log.info("上传照片时:"+e.getMessage());
			errors.addErrorString("上传相片失败");
			return errors.showErrorPage(model);
	    }
		String queryTitle = request.getParameter("queryTitle");
		String content = request.getParameter("content");
		noticeMng.save(queryTitle,content,photoUrl,miniUrl,request);
		adminLogMng.operating(request, "cms.add.notice", "add.notice.title:"+queryTitle);
		return "redirect:v_list.do";
	}
	
	@RequestMapping("o_edit.do")
	public String edit(HttpServletRequest request,HttpServletResponse response,ModelMap model,String noticeId){
		WebErrors errors = validateId(noticeId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		Notice bean = noticeMng.findById(Integer.parseInt(noticeId));
		String content = fileUtils.readFile(bean.getFileUrl());
		model.addAttribute("content", content);
		model.addAttribute("notice", bean);
		return "notice/edit";
	}
	@RequestMapping("o_update.do")
	public String update(HttpServletRequest request,HttpServletResponse response,ModelMap model,Notice notice,@RequestParam(value = "photo", required = false) MultipartFile photo){
		WebErrors errors = WebErrors.create(request);
		if(notice.getId() == null){
			errors.addErrorString("公告id为空");
			return errors.showErrorPage(model);
		}
		String fileUrl = request.getParameter("fileUrl");
		notice.setFileUrl(fileUrl);
		String content = request.getParameter("content");
		if(photo != null && !photo.isEmpty()){
			String photoUrl = null,miniUrl=null;
			String origName = photo.getOriginalFilename();
			String extName = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
			if(!"gif,jpg,jpeg,png,bmp".contains(extName)){
				errors.addErrorString("请上传后缀名为:gif,jpg,jpeg,png,bmp的照片");
				return errors.showErrorPage(model);
			}
			try {
				photoUrl=fileRepository.storeByExt("/upload/file", extName, photo);
				ServletContext  context=request.getSession().getServletContext();
				File fi = new File(context.getRealPath(photoUrl)); //大图文件  


				String miniPath=context.getRealPath("/upload/file");
				String miniName=UploadUtils.generateFilename("mini", extName);
				File fo = new File(miniPath,miniName); //将要转换出的小图文件
				miniUrl="/upload/file/"+miniName;
				imageScale.resizeFix(fi, fo,180,180);
				notice.setPhotoUrl(photoUrl);
				notice.setImagUrl(miniUrl);
			} catch (IOException e) {
				log.info("上传照片时:"+e.getMessage());
				errors.addErrorString("上传相片失败");
				return errors.showErrorPage(model);
			} catch (Exception e) {
				log.info("上传照片时:"+e.getMessage());
				errors.addErrorString("上传相片失败");
				return errors.showErrorPage(model);
		    }
		}
		try{
		     noticeMng.update(notice,content);
		     adminLogMng.operating(request, "cms.update.notice", "update.notice.id="+notice.getId());
		}catch(RuntimeException e){
			log.info("修改公告时:"+e.getMessage());
			errors.addErrorString("修改公告失败");
			return errors.showErrorPage(model);
		}
		return "redirect:v_list.do";
	}
	
	@RequestMapping("o_delete.do")
	public String delete(HttpServletRequest request,HttpServletResponse response,ModelMap model,String noticeId){
		WebErrors errors = validateId(noticeId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		Notice notice = noticeMng.deleteById(Integer.parseInt(noticeId));
		if(notice == null){
			errors.addErrorString("删除公告失败");
			return errors.showErrorPage(model);
		}
		adminLogMng.operating(request, "cms.delete.notice", "delete.notice.id="+noticeId);
		model.addAttribute("message", "global.success");
		return getNoticePage(request,response,model,null,null,0);
	}
	
	private WebErrors validateId(String noticeId, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(noticeId) || noticeId.trim().equals("")){
			errors.addErrorCode("error.required", "公告id");
			return errors;
		}
		return errors;
	}

	@Autowired
	private NoticeMng noticeMng;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private ImageScale imageScale;
	
	@Autowired
	private AdminLogMng adminLogMng;
	
	@Autowired
	private FileUtils fileUtils;
}
