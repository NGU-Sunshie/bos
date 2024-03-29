package com.itheima.bos.dao;


import java.util.List;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.domain.Region;

public interface IRegionDao extends IBaseDao<Region> {

	List<Region> findByQ(String q);

}
