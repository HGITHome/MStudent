package com.dgut.main.manager.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.common.upload.FileUtils;
import com.dgut.main.dao.FileDao;
import com.dgut.main.entity.Admin;
import com.dgut.main.entity.UploadFile;
import com.dgut.main.manager.FileMng;
import com.dgut.main.web.CmsUtils;

@Service
@Transactional
public class FileMngImpl implements FileMng{

	@Transactional(readOnly=true)
	public Pagination getPage(String queryFileName, Integer pageNo,
			Integer pageSize) {
		
		return dao.getPage(queryFileName, pageNo, pageSize);
	}

	@Transactional(readOnly=true)
	public UploadFile findById(Integer id) {
		
		return dao.findById(id);
	}

	
	public UploadFile save(String origName, String photoUrl,
			HttpServletRequest request){
		Admin admin = CmsUtils.getAdmin(request);
		UploadFile file = new UploadFile();
		file.setFileName(origName);
		file.setFilePath(photoUrl);
		file.setUploadTime(new Date());
		file.setDownNumber(0);
		file.setAdmin(admin);
		save(file);
		return file;
	}
	
	public UploadFile save(UploadFile bean) {
		
		return dao.save(bean);
	}

	
	public UploadFile update(UploadFile bean) {
		Updater<UploadFile> updater = new Updater<UploadFile>(bean);
		return dao.updateByUpdater(updater);
	}

	public UploadFile deleteByFilePath(UploadFile f){
		if(fileUtils.delFile(f.getFilePath())){
			 f = dao.deleteById(f.getId());
			 return f;
		}else{
			return null;
		}
	}
	
	public UploadFile deleteById(Integer id) {
		
		return dao.deleteById(id);
	}

	@Autowired
	private FileDao dao;
	
	@Autowired
	private FileUtils fileUtils;
}
