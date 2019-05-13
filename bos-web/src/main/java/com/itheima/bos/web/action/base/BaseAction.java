package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 表现层通用实现
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected PageBean pageBean = new PageBean();
	// 创建离线查询对象
	DetachedCriteria detachedCriteria = null;

	// 属性驱动接受页面提交参数
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}

	public static final java.lang.String HOME = "home";
	public static final java.lang.String LIST = "list";

	// 需要一个模型对象
	protected T model;

	// 通过父类BaseAction的构造方法动态获取实体类型，通过反射技术创建model对象
	public BaseAction() {
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		Class<T> entityClass = (Class) actualTypeArguments[0];
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		// 通过反射创建对象
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void java2Json(Object object, String[] exclude){
		// 使用json-lib将PageBean对象转为json，通过输出流写回页面中
		// JSONObject---将单一对象转为json
		// JSONArray----将数组或者集合对象转为json
		// 指定哪些属性不需要转json
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(exclude);
		String json = JSONObject.fromObject(object, jsonConfig).toString();
		printToWEB(json);
	}
	public void java2Json(List object, String[] exclude) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(exclude);
		String json = JSONArray.fromObject(object, jsonConfig).toString();
		printToWEB(json);
	}

	/**
	 * 把给定内容写回给页面
	 */
	public void printToWEB(Object object){
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public T getModel() {
		return model;
	}

}
