package com.dgut.main.manager.impl;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.common.upload.FileUtils;
import com.dgut.main.Constants;
import com.dgut.main.dao.NoticeDao;
import com.dgut.main.entity.Admin;
import com.dgut.main.entity.Notice;
import com.dgut.main.manager.NoticeMng;
import com.dgut.main.web.CmsUtils;

@Service
@Transactional
public class NoticeMngImpl implements NoticeMng {

	@Transactional(readOnly=true)
	public Pagination getPage(String queryTitle, String queryUsername,
			Integer pageNo, Integer pageSize) {
	
		return dao.getPage(queryTitle, queryUsername, pageNo, pageSize);
	}

	@Transactional(readOnly=true)
	public Notice findById(Integer id) {
		
		return dao.findById(id);
	}

	
	public Notice save(String queryTitle, String content, String photoUrl,
			String miniUrl, HttpServletRequest request){
		Admin admin = CmsUtils.getAdmin(request);
		Notice bean = new Notice();
		bean.setPhotoUrl(photoUrl);
		bean.setImagUrl(miniUrl);
		bean.setTitle(queryTitle);
		bean.setAdmin(admin);
		bean.setCreateTime(new Date());
		bean.setFileUrl(fileUtils.saveFile(Constants.FILE_PATH, content));
		save(bean);
		return bean;
	}
	
	public Notice save(Notice bean) {
		
		return dao.save(bean);
	}

	public Notice update(Notice notice, String content) throws RuntimeException{
		try {
			if(fileUtils.writeFileContent(notice.getFileUrl(), content)){
				update(notice);
			}
		} catch (IOException e) {
			throw new RuntimeException();
		}
		return notice;
	}
	
	public Notice update(Notice bean) {
		Updater<Notice> updater = new Updater<Notice>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public Notice deleteById(Integer id) {
		Notice bean = dao.findById(id);
		if(fileUtils.delFile(bean.getFileUrl())){
			return dao.deleteById(id);
		 }
		return null;
	}

	@Autowired
	private NoticeDao dao;
	
	@Autowired
	private FileUtils fileUtils;
}
