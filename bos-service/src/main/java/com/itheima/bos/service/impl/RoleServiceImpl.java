package com.itheima.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
	// 注入dao层
	@Resource
	private IRoleDao roleDao;

	@Override
	public void save(Role role, String functionIds) {
		roleDao.save(role);
		if (StringUtils.isNotBlank(functionIds)) {
			String[] ids = functionIds.split(",");
			for (String id : ids) {
				Function function = new Function(id);
				role.getFunctions().add(function);
			}
		}

	}

	@Override
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}

	@Override
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] roleIds = ids.split(",");
			for (String id : roleIds) {
				roleDao.delete(roleDao.findById(id));
			}
		}
	}
	
	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

}
