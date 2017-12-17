package com.dgut.main.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dgut.common.hibernate3.Updater;
import com.dgut.main.dao.DepartmentDao;
import com.dgut.main.entity.Department;
import com.dgut.main.entity.Major;
import com.dgut.main.manager.DepartmentMng;

@Service
@Transactional
public class DepartmentMngImpl implements DepartmentMng {
 
	@Transactional(readOnly=true)
	public List<Department> getList() {
	
		return dao.getList();
	}

	@Transactional(readOnly=true)
	public Department findById(Integer id) {
		
		return dao.findById(id);
	}

	public String getDepartmentString(List<Major> majorList){
		String majorString = toStr(majorList);
		return majorString;
	}
	
	

	public Department save(Department bean) {
		
		return dao.save(bean);
	}

	
	public Department update(Department bean) {
		Updater<Department> updater = new Updater<Department>(bean);
		return dao.updateByUpdater(updater);
	}

	
	public Department deleteById(Integer id) {
		
		return dao.deleteById(id);
	}
	
	private String toStr(List<Major> majorList) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < majorList.size() ; i ++ ){
			if(i != majorList.size()-1){
			  sb.append(majorList.get(i).getName()+",");
			}else{
				sb.append(majorList.get(i).getName() + "=");
			}
		}
		for(int i = 0 ; i < majorList.size() ; i ++ ){
			sb.append(majorList.get(i).getId()+",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	@Autowired
	private DepartmentDao dao;

}
