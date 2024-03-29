package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Role;
import com.itheima.bos.utils.PageBean;

public interface IRoleService {

	void save(Role model, String functionIds);

	void pageQuery(PageBean pageBean);

	void deleteBatch(String ids);

	List<Role> findAll();

}
