package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IRegionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Region;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao{
	/**
	 * 根据q参数进行模糊查询
	 */
	/*private String province;
	private String city;
	private String district;
	private String postcode;
	private String shortcode;
	private String citycode;*/
	@Override
	public List<Region> findByQ(String q) {
		String hql ="FROM Region WHERE province like ? OR city like ? OR "
				+ "district like ? OR postcode like ? OR shortcode like ? OR "
				+ "citycode like ?";
		return (List<Region>) getHibernateTemplate().find(hql, "%"+q+"%", "%"+q+"%", 
				"%"+q+"%", "%"+q+"%", "%"+q+"%", "%"+q+"%");
	}

}
