package com.itheima.bos.service;

import java.util.ArrayList;
import java.util.List;

import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;

public interface IRegionService {

	void saveBatch(ArrayList<Region> regionList);

	void pageQuery(PageBean pageBean);

	void save(Region model);

	void delete(String id);

	void update(Region model);

	String checkIdExists(String id);

	List<Region> listajax();

	List<Region> listajaxByQ(String q);

	
}
