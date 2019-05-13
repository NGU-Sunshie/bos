package com.itheima.bos.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IFunctionService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * 权限管理
 * 
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
	// 注入service层
	@Resource
	private IFunctionService functionService;

	/**
	 * 查询所有权限，返回json数据
	 */
	public String listajax() {
		List<Function> list = functionService.findAll();
		java2Json(list, new String[] { "roles", "parentFunction" });
		return NONE;
	}

	/**
	 * 权限添加功能
	 */
	public String add() {
		functionService.add(model);
		return LIST;
	}

	/**
	 * 分业查询功能
	 */
	public String pageQuery() {
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		functionService.pageQuery(pageBean);
		java2Json(pageBean, new String[] { "roles", "parentFunction", "children" });
		return NONE;
	}

	// 属性驱动接受页面提交过来的id串
	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 权限批量删除功能
	 */
	public String deleteBatch() {
		functionService.deleteBatch(ids);
		return LIST;
	}
	/**
	 * 根据当前登录用户查询对应的菜单
	 */
	public String findMenu(){
		User user = BOSUtils.getLoginUser();
		List<Function>  list = null;
		if(user.getUsername().equals("admin")){
			 list = functionService.findAllMenu();
		}else{
			list = functionService.findMenuByUserId(user.getId());
		}
		java2Json(list, new String[]{"roles", "parentFunction" ,"children"});
		return NONE;
	}
}
