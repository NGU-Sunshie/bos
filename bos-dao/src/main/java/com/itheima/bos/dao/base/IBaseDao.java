package com.itheima.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.utils.PageBean;

/**
 * 持久层通用接口
 * @author Administrator
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	  public void save(T entity);
	  public void delete(T entity);
	  public void update(T entity);
	  public void saveOrUpdate(T entity);
	  
	  public T findById(Serializable id);
	  public List<T> findAll();
	  public void executeUpdate(String queryName, Object ...objects);
	  public void pageQuery(PageBean pageBean);
	  public String checkIdExists(Serializable id);
	  public List<T> findByCriteria(DetachedCriteria detachedCriteria);
}
