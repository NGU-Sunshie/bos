package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Function;
import com.itheima.bos.utils.PageBean;

public interface IFunctionService {

	List<Function> findAll();

	void add(Function model);

	void pageQuery(PageBean pageBean);

	void deleteBatch(String ids);

	List<Function> findAllMenu();

	List<Function> findMenuByUserId(String id);

}
