package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

public interface IStaffService {

	void save(Staff model);

	void pageQuery(PageBean pageBean);

	void deleteBatch(String ids);

	Staff findByid(String id);

	void update(Staff staff);

	void restore(String ids);

	List<Staff> findListNotDelete();

}
