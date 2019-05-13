package com.itheima.bos.web.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.service.IDecidedzoneService;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.Customer;
import com.itheima.crm.ICustomerService;

/**
 * 定区管理
 * 
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	@Autowired
	private IDecidedzoneService decidedzoneService;
	// 属性驱动接收多个分区ID
	private String[] Subareaid;

	public void setSubareaid(String[] subareaid) {
		Subareaid = subareaid;
	}

	/**
	 * 添加定区
	 */
	public String add() {
		decidedzoneService.save(model, Subareaid);
		return LIST;
	}

	/**
	 * 分页查询方法
	 * 
	 */
	public String pageQuery() {
		decidedzoneService.pageQuery(pageBean);
		java2Json(pageBean, new String[] { "currentPage", "detachedCriteria", "pageSize", "subareas", "decidedzones" });
		return NONE;
	}

	// 注入crm服务代理对象
	@Autowired
	private ICustomerService proxy;

	/**
	 * 查询未关联到定区的客户
	 * 
	 * @return
	 */
	public String findListNotAssociation() {
		List<Customer> list = proxy.findListNotAssociation();
		java2Json(list, new String[] {});
		return NONE;
	}

	/**
	 * 查询已经关联到定区的客户
	 * 
	 * @return
	 */
	public String findListHasAssociation() {
		List<Customer> list = proxy.findListHasAssociation(model.getId());
		if(list != null){
			java2Json(list, new String[] {});
		}else{
			List<Customer> list1 = new ArrayList<Customer>();
			list1.add(new Customer());
			java2Json(list1, new String[] {});
		}
		
		return NONE;
	}

	// 属性驱动接受页面的客户Id
	private List<Integer> customerIds;

	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}

	/**
	 * 关联到定区的客户
	 * 
	 * @return
	 */
	public String assigncustomerstodecidedzone() {
		proxy.assigncustomerstodecidedzone(model.getId(), customerIds);
		return LIST;
	}
}
