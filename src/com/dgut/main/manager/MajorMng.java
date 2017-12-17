package com.dgut.main.manager;

import java.util.ArrayList;
import java.util.List;

import com.dgut.main.entity.BClass;
import com.dgut.main.entity.Major;

public interface MajorMng {
	
    public List<Major> getList();
	
	public Major findById(Integer id);
	
	public Major save(Major bean);
	
	public Major update(Major bean);
	
	public Major deleteById(Integer id);

	public String getDataString(List<BClass> arrayList);
	
}
