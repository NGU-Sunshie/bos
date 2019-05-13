package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IFunctionDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {
	//注入dao
	@Autowired
	private IFunctionDao functionDao;

	@Override
	public List<Function> findAll() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
		detachedCriteria.add(Restrictions.isNull("parentFunction.id"));
		return functionDao.findByCriteria(detachedCriteria );
	}

	@Override
	public void add(Function model) {
		functionDao.save(model);
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

	@Override
	public void deleteBatch(String ids) {
		String[] split = ids.split(",");
		for (String id : split) {
				functionDao.delete(functionDao.findById(id));
		}
	}


	@Override
	public List<Function> findAllMenu() {
		// TODO Auto-generated method stub
		return functionDao.findAllMenu();
	}

	@Override
	public List<Function> findMenuByUserId(String id) {
		// TODO Auto-generated method stub
		return functionDao.findMenuByUserId(id);
	}
}
