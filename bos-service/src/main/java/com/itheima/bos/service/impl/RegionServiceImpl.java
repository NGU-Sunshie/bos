package com.itheima.bos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.dao.ISubareaDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements IRegionService{
	@Autowired
	private IRegionDao RegionDao;
	@Autowired
	private ISubareaDao subareaDao;
	/**
	 * 区域数据批量保存
	 */
	public void saveBatch(ArrayList<Region> regionList) {
		for (Region region : regionList) {
			RegionDao.saveOrUpdate(region);
		}
		
	}
	/**
	 * 分页查询
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		RegionDao.pageQuery(pageBean);
		
	}
	/**
	 * 区域保存
	 */
	@Override
	public void save(Region model) {
		RegionDao.save(model);
		
	}
	/**
	 * 区域删除
	 */
	@Override
	public void delete(String id) {
		Region region = RegionDao.findById(id);
		// TODO 删除前，应该把以该区域ID为外键的分区对象的外键置为null;(或者级联删除分区对象)
		//应该也可以来一波级联删除的
		subareaDao.executeUpdate("subarea.deleteFK", id);
		RegionDao.delete(region);
	}
	/**
	 * 区域修改功能
	 */
	@Override
	public void update(Region model) {
		RegionDao.update(model);
	}
	/**
	 * 检查区域编号是否存在
	 */
	@Override
	public String checkIdExists(String id) {
		return RegionDao.checkIdExists(id);
	}
	/**
	 * 查询所有区域
	 */
	@Override
	public List<Region> listajax() {
		return RegionDao.findAll();
	}
	/**
	 * 根据页面输入进行模糊查询
	 */
	@Override
	public List<Region> listajaxByQ(String q) {
		return RegionDao.findByQ(q);
	}

}
