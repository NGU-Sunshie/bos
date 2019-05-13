package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IWorkordermanageDao;
import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService {
	//注入数据库操作层
	@Autowired
	private IWorkordermanageDao workordermanageDao;
	/**
	 * 工作单保存功能
	 */
	@Override
	public void save(Workordermanage model) {
		workordermanageDao.saveOrUpdate(model);
	}
	/**
	 * 工作单分业查询功能
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		workordermanageDao.pageQuery(pageBean);
	}
}
