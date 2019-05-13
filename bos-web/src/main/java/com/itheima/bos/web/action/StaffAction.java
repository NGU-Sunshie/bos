package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffService;
import com.itheima.bos.web.action.base.BaseAction;

/**
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	@Autowired
	private IStaffService staffService;

	/**
	 * 取派员添加
	 */
	public String add() {
		staffService.save(model);
		return LIST;
	}

	
	/**
	 * 分页查询方法
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException {
		staffService.pageQuery(pageBean);
		java2Json(pageBean,new String[] { "currentPage", "detachedCriteria", "pageSize","decidedzones" });
		return NONE;
	}
	//属性驱动接受页面提交过来的id串
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 取派员批量删除功能
	 */
	@RequiresPermissions("staff.delte")
	public String deleteBatch(){
		staffService.deleteBatch(ids);
		return LIST;
	}
	/**
	 * 取派员批量还原功能
	 */
	public String restore(){
		staffService.restore(ids);
		return LIST;
	}
	/**
	 * 取派员修改功能
	 */
	public String edit(){
		//查询数据库获取原始数据
		Staff staff = staffService.findByid(model.getId());
		staff.setHaspda(model.getHaspda());
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		staffService.update(staff);
		return LIST;
	}
	/**
	 * 查询所有未删除的取派员，返回json
	 */
	public String listajax(){
		List<Staff> list = staffService.findListNotDelete();
		this.java2Json(list, new String[]{"decidedzones"});
		return NONE;
	}
}

