package com.itheima.bos.web.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Workordermanage;
import com.itheima.bos.service.IWorkordermanageService;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 工作单管理
 * 
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage> {
	// 注入服务层
	@Autowired
	private IWorkordermanageService workordermanageSerivce;

	/**
	 * 工作单添加功能
	 * 
	 * @throws IOException
	 */
	public String add() throws IOException {
		String flag = "1";
		try {
			workordermanageSerivce.save(model);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			flag = "0";
		}
		printToWEB(flag);
		return NONE;
	}

	/**
	 * 工作单分业查询功能
	 */
	public String pageQuery() {
		workordermanageSerivce.pageQuery(pageBean);
		java2Json(pageBean, new String[] {});
		return NONE;
	}
}
