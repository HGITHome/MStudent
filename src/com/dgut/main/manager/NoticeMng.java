package com.dgut.main.manager;

import javax.servlet.http.HttpServletRequest;

import com.dgut.common.page.Pagination;
import com.dgut.main.entity.Notice;

public interface NoticeMng {

	    public Pagination getPage(String queryTitle,String queryUsername,Integer pageNo,Integer pageSize);
		
		public Notice findById(Integer id);
		
		public Notice save(Notice bean);
		
		public Notice update(Notice updater);
		
		public Notice deleteById(Integer id);

		public Notice save(String queryTitle, String content, String photoUrl,
				String miniUrl, HttpServletRequest request);

		public Notice update(Notice notice, String content) throws RuntimeException;
}
