package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Role;
import com.itheima.bos.service.IRoleService;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 角色管理
 * 
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	// 注入service层
	@Autowired
	private IRoleService roleService;
	// 属性接受页面提交的权限Id
	private String functionIds;

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	/**
	 * 角色添加功能
	 * 
	 */
	public String add() {
		roleService.save(model, functionIds);
		return LIST;
	}

	/**
	 * 分页查询功能
	 */
	public String pageQuery() {
		roleService.pageQuery(pageBean);
		java2Json(pageBean, new String[] { "functions", "users" });
		return NONE;
	}

	// 属性驱动接受页面提交过来的id串
	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 角色批量删除功能
	 */
	public String deleteBatch() {
		roleService.deleteBatch(ids);
		return LIST;
	}
	/**
	 * 查询所有权限返回json数据
	 */
	public String listajax(){
		List<Role> list = roleService.findAll();
		java2Json(list, new String[]{"functions", "users"});
		return NONE;
	}
}
