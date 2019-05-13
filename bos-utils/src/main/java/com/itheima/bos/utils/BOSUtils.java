package com.itheima.bos.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.domain.User;

/**
 * BOS项目的工具类
 * @author Administrator
 *
 */
public class BOSUtils {
	//获得登录用户对象
	public static User getLoginUser(){
		return  (User) getSession().getAttribute("loginUser");
	}
	//获得Session对象
	public static HttpSession getSession() {
		// TODO Auto-generated method stub
		return  ServletActionContext.getRequest().getSession();
	}
}
