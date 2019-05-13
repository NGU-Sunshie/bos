package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {
	@Autowired
	private ISubareaDao subareaDao;

	/**
	 * 分区保存
	 */
	@Override
	public void save(Subarea model) {
		subareaDao.save(model);
	}

	/**
	 * 分页查询
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
	}

	/**
	 * 检查分区编号是否存在
	 */
	@Override
	public String checkIdExists(String id) {
		return subareaDao.checkIdExists(id);
	}

	/**
	 * 分区修改
	 */
	@Override
	public void update(Subarea model) {
		subareaDao.update(model);
	}

	/**
	 * 根据ID获得实体对象
	 */
	@Override
	public Subarea findByid(String id) {
		return subareaDao.findById(id);
	}

	/**
	 * 根据ID删除实体对象
	 */
	@Override
	public void delete(String id) {
		subareaDao.delete(findByid(id));
	}

	/**
	 * 分区导出功能
	 */
	@Override
	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}

	/**
	 * 查询所有未关联到定区的分区，返回json
	 * 
	 * @return
	 */
	@Override
	public List<Subarea> findListNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(detachedCriteria);
	}

	/**
	 * 根据定区Id查询关联的分区
	 */
	@Override
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
		return subareaDao.findByCriteria(detachedCriteria);
	}

}
