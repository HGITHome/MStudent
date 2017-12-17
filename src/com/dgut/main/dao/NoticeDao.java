package com.dgut.main.dao;


import com.dgut.common.hibernate3.Updater;
import com.dgut.common.page.Pagination;
import com.dgut.main.entity.Notice;

public interface NoticeDao {

    public Pagination getPage(String queryTitle,String queryUsername,Integer pageNo,Integer pageSize);
	
	public Notice findById(Integer id);
	
	public Notice save(Notice bean);
	
	public Notice updateByUpdater(Updater<Notice> updater);
	
	public Notice deleteById(Integer id);
}
