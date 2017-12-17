package com.dgut.main.manager;

import javax.servlet.http.HttpServletRequest;

import com.dgut.common.page.Pagination;
import com.dgut.main.entity.UploadFile;

public interface FileMng {

	public Pagination getPage(String queryFileName,Integer pageNo,Integer pageSize);
	
	public UploadFile findById(Integer id);
	
	public UploadFile save(UploadFile bean);
	
	public UploadFile update(UploadFile bean);
	
	public UploadFile deleteById(Integer id);

	public UploadFile save(String origName, String fileUrl,
			HttpServletRequest request);

	public UploadFile deleteByFilePath(UploadFile f);
}
