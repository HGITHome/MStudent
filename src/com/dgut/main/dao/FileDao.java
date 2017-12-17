package com.dgut.main.dao;

import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.UploadFile;

public interface FileDao {

	    public Pagination getPage(String queryFileName,Integer pageNo,Integer pageSize);
		
		public UploadFile findById(Integer id);
		
		public UploadFile save(UploadFile bean);
		
		public UploadFile updateByUpdater(Updater<UploadFile> updater);
		
		public UploadFile deleteById(Integer id);
}
