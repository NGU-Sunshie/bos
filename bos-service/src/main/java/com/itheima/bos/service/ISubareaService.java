package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

public interface ISubareaService {

	void save(Subarea model);

	void pageQuery(PageBean pageBean);

	String checkIdExists(String id);

	void update(Subarea model);

	Subarea findByid(String id);

	void delete(String id);

	List<Subarea> findAll();

	List<Subarea> findListNotAssociation();

	List<Subarea> findListByDecidedzoneId(String decidedzoneId);

}
