package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IStaffDao;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService {
	@Autowired
	private IStaffDao staffDao;

	// 添加取派员
	public void save(Staff model) {
		staffDao.save(model);
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);

	}

	/**
	 * 取派员批量删除 逻辑删除，将deltag改为1
	 */
	@Override
	public void deleteBatch(String ids) {
		if (StringUtils.isNoneBlank(ids)) {
			String[] staffids = ids.split(",");
			for (String id : staffids) {
				staffDao.executeUpdate("staff.delete", id);
			}

		}
	}
	/**
	 * 取派员批量还原 逻辑还原，将deltag改为0
	 */
	@Override
	public void restore(String ids) {
		if (StringUtils.isNoneBlank(ids)) {
			String[] staffids = ids.split(",");
			for (String id : staffids) {
				staffDao.executeUpdate("staff.restore", id);
			}
			
		}
	}

	/**
	 * 根据Id获取取派员
	 */
	@Override
	public Staff findByid(String id) {
		
		return staffDao.findById(id);
	}
	/**
	 * 取派员修改功能
	 */
	@Override
	public void update(Staff staff) {
		staffDao.update(staff);

	}
	/**
	 * 查询所有未删除的取派员，返回json
	 */
	@Override
	public List<Staff> findListNotDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		//添加过滤条件
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		return staffDao.findByCriteria(detachedCriteria);
	}

	
}
