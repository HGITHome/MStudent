package com.dgut.main.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.main.dao.MajorDao;
import com.dgut.main.entity.BClass;
import com.dgut.main.entity.Major;
import com.dgut.main.manager.MajorMng;

@Service
@Transactional
public class MajorMngImpl implements MajorMng {

	@Transactional(readOnly=true)
	public List<Major> getList() {
		
		return dao.getList();
	}

	@Transactional(readOnly=true)
	public Major findById(Integer id) {
		
		return dao.findById(id);
	}

	
	public Major save(Major bean) {
		
		return dao.save(bean);
	}

	
	public Major update(Major bean) {
		Updater<Major> updater = new Updater<Major>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public Major deleteById(Integer id) {
		
		return dao.deleteById(id);
	}
	
	
	public String getDataString(List<BClass> arrayList){
		String data = setToStr(arrayList);
		return data;
	}
	
	private String setToStr(List<BClass> arrayList) {
	    StringBuffer sb = new StringBuffer();
	    sb.delete(0, sb.length());
	    if(arrayList.size() == 0){
	    	return sb.toString();
	    }else{
	    	for(int i = 0 ; i < arrayList.size() ; i ++ ){
	    		sb.append(arrayList.get(i).getClassName()+",");
	    	}
	    	sb.replace(sb.length()-1, sb.length(), "=");
	    	for(int i = 0 ; i < arrayList.size() ; i ++){
	    		sb.append(arrayList.get(i).getId()+",");
	    	}
	    	sb.deleteCharAt(sb.length()-1);
	    }
		return sb.toString();
	}

	@Autowired
	private MajorDao dao;

}
