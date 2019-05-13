package com.itheima.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.service.INoticebillService;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;

/**
 * 业务通知单管理
 * 
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {
	@Autowired
	private INoticebillService noticebillService;
	// 注入crm服务
	@Autowired
	private ICustomerService proxy;

	/**
	 * 远程掉调用crm服务，根据手机号获取客户信息
	 */
	public String findCustomerByTelephone() {
		Customer customer = proxy.findCustomerByTelephone(model.getTelephone());
		java2Json(customer, new String[] {});
		return NONE;
	}
	/**
	 * 保存业务通知单
	 */
	public String add(){
		noticebillService.save(model);
		return "noticebill_add";
	}
}
