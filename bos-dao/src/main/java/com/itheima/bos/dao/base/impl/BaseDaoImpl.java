package com.itheima.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.bos.dao.base.IBaseDao;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

/**
 * 持久层通用实现
 * @author Administrator
 *
 * @param <T>
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	//代表某个实体的类型
	private Class<T> entityClass;
	
	@Resource//根据类型注入Spring工厂中的会话工厂对象
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	//在父类(BaseDaoImpl)的构造方法中动态获取entityClass
	public BaseDaoImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}

	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		String hql = "From " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	@Override
	public void executeUpdate(String queryName, Object... objects) {
		Query namedQuery = getSessionFactory().getCurrentSession().getNamedQuery(queryName);
		int i = 0;
		for (Object object : objects) {
			//为查询语句赋值
			namedQuery.setParameter(i++, object);
		}
		//执行更新
		namedQuery.executeUpdate();
	}
	/**
	 * 通用分页查询
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		int pageSize = pageBean.getPageSize();
		int currentPage = pageBean.getCurrentPage();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//查询总记录数total
		//指定hibernate框架发出sql的形式----》select count(*) from bc_staff;
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> countlist = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long count = countlist.get(0);
		pageBean.setTotal(count.intValue());
		//查询当前页需要展示的数据集合rows
		detachedCriteria.setProjection(null);
		//指定hibernate框架封装对象的形式
		detachedCriteria.setResultTransformer(detachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage - 1) * pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}
	/**
	 * 检查给定的id是否存在
	 * true:不存在
	 * false:存在
	 */
	@Override
	public String checkIdExists(Serializable id) {
		try {
			T entity = findById(id);
			if(entity == null){
				return "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
		return "false";
	}

	@Override
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}

}
