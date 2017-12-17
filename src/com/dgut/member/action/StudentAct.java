package com.dgut.member.action;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.dgut.common.page.SimplePage.cpn;

import com.dgut.common.image.ImageScale;
import com.dgut.common.page.Pagination;
import com.dgut.common.upload.FileRepository;
import com.dgut.common.upload.UploadUtils;
import com.dgut.common.util.DateUtils;
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
import com.dgut.member.entity.StudentInfo;
import com.dgut.member.manager.MemberMng;
import com.dgut.member.manager.StudentInfoMng;

@Controller
@RequestMapping("student")
public class StudentAct {
	
	@RequestMapping("v_list.do")
	public String getPage(HttpServletRequest request,HttpServletResponse response,ModelMap model,String queryMajorName,String queryStudentName,String queryStudentNumber,Integer pageNo){
		//System.out.println(request.getParameter("classId"));
		Pagination pagination = studentInfoMng.getPage(queryMajorName,queryStudentName, queryStudentNumber, cpn(pageNo), CookieUtils.getPageSize(request));
		Admin admin = CmsUtils.getAdmin(request);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("queryMajorName", queryMajorName);
		model.addAttribute("queryStudentName", queryStudentName);
		model.addAttribute("queryStudentNumber", queryStudentNumber);
		model.addAttribute("pagination", pagination);
		model.addAttribute("admin", admin);
		return "student/list";
	}
	
	@RequestMapping("v_add.do")
	public String studentAdd(HttpServletRequest request,HttpServletResponse response ,ModelMap model){
		List<Department> departmentList = departmentMng.getList();
		List<Major> majorList = majorMng.getList();
		List<BClass> classList = classMng.getList();
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("majorList", majorList);
		model.addAttribute("classList", classList);
		return "student/add";
	}
	
	@RequestMapping(value="/ajaxMajor.do",method=RequestMethod.POST)
	public void ajaxMajor(HttpServletRequest request,HttpServletResponse response,ModelMap model,Integer id) throws Exception, IOException{
		String data = null;
		if(id != null){
		  Department department= departmentMng.findById(id);
		  data  = departmentMng.getDepartmentString(new ArrayList<Major>(department.getMajorSet()));
		}else{
			data = departmentMng.getDepartmentString(majorMng.getList());
		}
		response.getOutputStream().write(data.getBytes("UTF-8"));
	}
	
	@RequestMapping(value="/ajaxClass.do" , method=RequestMethod.POST)
	public void ajaxClass(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception, IOException{
		String data = null;
		if(id != null){
			Major major = majorMng.findById(id);
			data = majorMng.getDataString(new ArrayList<BClass>(major.getClassSet()));
		}else{
			data = majorMng.getDataString(classMng.getList());
		}
		response.getOutputStream().write(data.getBytes("UTF-8"));
	}
	
	@RequestMapping("o_save.do")
	public String save(HttpServletRequest request,HttpServletResponse response,ModelMap model,@RequestParam(value = "photo", required = false) MultipartFile photo){
		StudentInfo bean = new StudentInfo();
		bean.setName(request.getParameter("realname"));
		bean.setSchoolNumber(request.getParameter("schoolNumber"));
		bean.setGender(Integer.parseInt(request.getParameter("gender")));
		String departmentId = request.getParameter("departmentList");
		String majorId = request.getParameter("majorList");
		String classId = request.getParameter("classList");
		WebErrors errors = validateSave(departmentId,majorId,classId,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		bean.setMajor(majorMng.findById(Integer.parseInt(majorId)));
		bean.setBclass(classMng.findById(Integer.parseInt(classId)));
		bean.setNation(request.getParameter("nation"));
		bean.setPolitical_status(request.getParameter("political_status"));
		bean.setNickname(request.getParameter("nickname"));
		bean.setID(request.getParameter("ID"));
		try {
			bean.setBirthday(DateUtils.format2.parse(request.getParameter("birthday")));
		} catch (ParseException e) {
			errors.addErrorString("日期解析失败!");
			return errors.showErrorPage(model);
		}
		bean.setNative(request.getParameter("native"));
		try {
			bean.setEntranceTime(DateUtils.format2.parse(request.getParameter("entrance_time")));
		} catch (ParseException e) {
		   errors.addErrorString("日期解析失败!");
		   return errors.showErrorPage(model);
		}
		bean.setEducation(request.getParameter("education"));
		bean.setAddress(request.getParameter("address"));
		bean.setHomePhone(request.getParameter("home_phone"));
		bean.setPostalcode(request.getParameter("postalcode"));
		bean.setSelfPhone(request.getParameter("self_phone"));
		bean.setEmail(request.getParameter("email"));
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
				errors.addErrorString("上传相片失败");
				return errors.showErrorPage(model);
			} catch (Exception e) {
				errors.addErrorString("上传相片失败");
				return errors.showErrorPage(model);
		    }
		bean.setIconUrl(photoUrl);
		bean.setMiniUrl(miniUrl);
		bean.setMember(memberMng.findById(1));
		studentInfoMng.save(bean);
		adminLogMng.operating(request, "cms.student.infomation.add", "add.student.information.schoolnumber="+bean.getSchoolNumber());
		return "redirect:v_list.do";
	}
	
	@RequestMapping("o_info.do")
	public String info(HttpServletRequest request,HttpServletResponse response,ModelMap model,String id){
		WebErrors errors = validateId(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		StudentInfo student = studentInfoMng.findById(Integer.parseInt(id));
		model.addAttribute("student", student);
		return "student/info";
	}
	
	@RequestMapping("o_edit.do")
	public String edit(HttpServletRequest request,HttpServletResponse response,ModelMap model,String id){
		WebErrors errors = validateId(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		StudentInfo student = studentInfoMng.findById(Integer.parseInt(id));
		List<Department> departmentList = departmentMng.getList();
		List<Major> majorList = majorMng.getList();
		List<BClass> classList = classMng.getList();
		model.addAttribute("departmentList", departmentList);
		model.addAttribute("majorList", majorList);
		model.addAttribute("classList", classList);
		model.addAttribute("student", student);
		return "student/edit";
	}
	
	@RequestMapping("o_update.do")
	public String update(HttpServletRequest request,HttpServletResponse response,ModelMap model,StudentInfo student,@RequestParam(value = "photo", required = false) MultipartFile photo){
		WebErrors errors = WebErrors.create(request);
		String majorId = request.getParameter("majorList");
		if(!StringUtils.isBlank(majorId)){
			student.setMajor(majorMng.findById(Integer.parseInt(majorId)));
		}
		String classId = request.getParameter("classList");
		if(!StringUtils.isBlank(classId)){
		   student.setBclass(classMng.findById(Integer.parseInt(classId)));	
		}
		String photoUrl = null,miniUrl=null;
		if(photo != null && !photo.isEmpty()){
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
				errors.addErrorString("上传相片失败");
				return errors.showErrorPage(model);
			} catch (Exception e) {
				errors.addErrorString("上传相片失败");
				return errors.showErrorPage(model);
		    }
			student.setIconUrl(photoUrl);
			student.setMiniUrl(miniUrl);
		}
		studentInfoMng.update(student);
		return "redirect:v_list.do";
	}
	
	@RequestMapping("o_delete.do")
	public String studentDelete(HttpServletRequest request,HttpServletResponse response,ModelMap model,String id){
		WebErrors errors = validateId(id,request);
		if(errors.hasErrors()){
			return errors.showErrorPage(model);
		}
		studentInfoMng.deleteById(Integer.parseInt(id));
		adminLogMng.operating(request, "cms.student.information.delete", "delete.student.information.id="+id);
		return "redirect:v_list.do";
	}
	
	private WebErrors validateId(String id, HttpServletRequest request) {
		WebErrors errors =  WebErrors.create(request);
		if(errors.ifNull(id, "id")){
			return errors;
		}
		return errors;
	}

	private WebErrors validateSave(String departmentId, String majorId,
			String classId, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(StringUtils.isBlank(departmentId) || StringUtils.isBlank(majorId)||StringUtils.isBlank(classId)){
			errors.addErrorString("请选择院系专业班级!");
			return errors;
		}
		return errors;
	}

	@Autowired
	private StudentInfoMng studentInfoMng;
	
	@Autowired
	private DepartmentMng departmentMng;
	
	@Autowired
	private MajorMng majorMng;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private ImageScale imageScale;
	
	
	@Autowired
	private ClassMng classMng;
	
	@Autowired
	private MemberMng memberMng;
	
	@Autowired
	private AdminLogMng adminLogMng;
	
}
