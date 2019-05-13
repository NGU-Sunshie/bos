package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.utils.PageBean;

public interface IWorkordermanageService {

	void save(Workordermanage model);

	void pageQuery(PageBean pageBean);

}
