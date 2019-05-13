package com.itheima.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.User;
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {
	/**
	 * 根据用户名密码查询用户 
	 */
	@Override
	public User findUserByUserNameAndPassword(String username, String password) {
		String hql = "FROM User u WHERE u.username = ? AND u.password = ?";
		List<User> result = (List<User>) getHibernateTemplate().find(hql, username,password);		
		if(result != null && result.size()>0){
			return result.get(0);
		}
		return null;
	}

}
