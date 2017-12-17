package com.dgut.main.action.main;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

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
import com.dgut.common.web.CookieUtils;
import com.dgut.main.Constants;
import com.dgut.main.entity.UploadFile;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.FileMng;
import com.dgut.main.web.WebErrors;

@Controller
@RequestMapping("file")
public class FileAct {

	private static final Logger log = LoggerFactory.getLogger(FileAct.class);
	
	@RequestMapping("v_list.do")
	public String getPage(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryFileName,Integer pageNo){
		Pagination pagination = fileMng.getPage(queryFileName, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryFileName", queryFileName);
		model.addAttribute("pagination", pagination);
		return "file/list";
	}
	
	@RequestMapping("o_upload.do")
	public String upload(HttpServletRequest request,HttpServletResponse response,ModelMap model,@RequestParam("file") MultipartFile file){
		WebErrors errors = WebErrors.create(request);
		String fileUrl = null;
		String origName = file.getOriginalFilename();
		String extName = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
		if(!"txt,doc,docx,xls".contains(extName)){
			errors.addErrorString("请上传后缀名为:txt,doc,docx,xls的文件");
			return errors.showErrorPage(model);
		}
		try {
			fileUrl=fileRepository.storeByExt(Constants.UPLOAD_FILE_PATH, extName, file);
			fileMng.save(origName,fileUrl,request);
			adminLogMng.operating(request, "cms.upload.file", "upload.file.name="+origName);
		} catch (IOException e) {
			log.info("上传文件时:"+e.getMessage());
			errors.addErrorString("上传文件失败");
			return errors.showErrorPage(model);
		} catch (Exception e) {
			log.info("上传文件时:"+e.getMessage());
			errors.addErrorString("上传文件失败");
			return errors.showErrorPage(model);
	    }
		model.addAttribute("message", "global.success");
		return getPage(request,response,model,null,0);
	}
	
	@RequestMapping("o_delete.do")
	public String delete(HttpServletRequest request,HttpServletResponse response,ModelMap model,String fileId){
		WebErrors errors = validateId(fileId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		UploadFile f = fileMng.findById(Integer.parseInt(fileId));
		f = fileMng.deleteByFilePath(f);
		if(f == null){
			errors.addErrorString("删除文件失败");
			return errors.showErrorPage(model);
		}
		adminLogMng.operating(request, "cms.delete.file", "delete.file.name="+f.getFileName());
		model.addAttribute("message", "global.success");
		return getPage(request,response,model,null,0);
	}
	
	@RequestMapping("o_down.do")
	 public  void FilesDownload(HttpServletRequest request, HttpServletResponse response,ModelMap model, String fileId) {
		 UploadFile f = fileMng.findById(Integer.parseInt(fileId));
		 f.setDownNumber(f.getDownNumber()+1);
		 String realPath = request.getSession().getServletContext().getRealPath(f.getFilePath());
	     File file = new File(realPath);
		 String filenames = file.getName();
		 InputStream inputStream;
		 try {
		       inputStream = new BufferedInputStream(new FileInputStream(file));
		       byte[] buffer = new byte[inputStream.available()];
		       inputStream.read(buffer);
		       inputStream.close();
		       response.reset();
		       // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
		       response.addHeader("Content-Disposition", "attachment;filename=" + new String(filenames.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
		       response.addHeader("Content-Length", "" + file.length());
		       OutputStream os = new BufferedOutputStream(response.getOutputStream());
		       response.setContentType("application/octet-stream");
		       os.write(buffer);// 输出文件
		       fileMng.update(f);
		       os.flush();
		       os.close();
		   } catch (Exception e) {
		       e.printStackTrace();
	    }
	 }
	
	
	
	private WebErrors validateId(String fileId, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(fileId) || fileId.trim().equals("")){
			errors.addErrorCode("error.required", "文件id");
			return errors;
		}
		return errors;
	}

	@Autowired
	private FileMng fileMng;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private ImageScale imageScale;
	
	@Autowired
	private AdminLogMng adminLogMng;
}
