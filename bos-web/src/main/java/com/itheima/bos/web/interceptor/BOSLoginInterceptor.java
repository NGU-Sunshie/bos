package com.itheima.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 自定义拦截器，实现未登录用户自动跳转到登录页面
 * @author Administrator
 *
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor {

	//拦截方法
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// 从session中获取用户对象
		User user = BOSUtils.getLoginUser();
		if(user != null){
			//用户已经登录
			return invocation.invoke();
		}
		//用户还未登录
		return "login";
	}

}
