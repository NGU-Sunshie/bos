package com.itheima.bos.web.action;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserSevice;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	//属性驱动接受页面输入的验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	@Autowired
	private IUserSevice userSevice;
	/**
	 * 用户登录 
	 */
	public String login() {
		//从session域中获取生成的验证码
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if(checkcode == null){
			checkcode = "";
		}
		//判断验证码是否正确
		if(StringUtils.isNotBlank(validatecode) && checkcode.equals(validatecode)){
			//使用shiro框架认证
			Subject subject = SecurityUtils.getSubject();
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),MD5Utils.md5(model.getPassword()));
			try {
				subject.login(token);
			} catch (Exception e) {
				//有异常，登录失败
				addActionError("用户名或密码不正确");
				return LOGIN;
			}
			//没有异常，登录成功
			//将登录用户放到session中
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			return HOME;
		}else{
			//验证码不正确
			addActionError("验证码不正确");
			return LOGIN;
		}
	}
	/**
	 * 用户登录 备份
	 */
	public String loginbak() {
		//从session域中获取生成的验证码
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		if(checkcode == null){
			checkcode = "";
		}
		//判断验证码是否正确
		if(StringUtils.isNotBlank(validatecode) && checkcode.equals(validatecode)){
			User user = userSevice.login(model);
			if(user != null){
				//登录成功
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return HOME;
			}else{
				//登录失败
				addActionError("用户名或密码不正确");
				return LOGIN;
			}
		}else{
			//验证码不正确
			addActionError("验证码不正确");
			return LOGIN;
		}
	}
	
	
	/**
	 * 用户注销
	 */
	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
	/**
	 * 修改登录密码
	 * @throws IOException 
	 */
	public String editPassword() throws IOException {
		String flag = "1";
		// 获取登录的用户
		User user = BOSUtils.getLoginUser();
		try {
			userSevice.editPassword(user.getId(),model.getPassword());
			
		} catch (Exception e) {
			flag = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	//属性驱动接收角色id
	private String[] roleIds;
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	/**
	 * 用户添加功能
	 */
	public String add(){
		userSevice.save(model,roleIds);
		return LIST;
	}
	/**
	 * 分页查询功能
	 */
	public String pageQuery(){
		userSevice.pageQuery(pageBean);
		java2Json(pageBean, new String[]{"noticebills","roles"});
		return NONE;
	}
}
