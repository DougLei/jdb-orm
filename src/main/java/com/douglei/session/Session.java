package com.douglei.session;

import com.douglei.session.impl.EntityMap;

/**
 * 和数据库交互的session接口
 * @author DougLei
 */
public interface Session {

	/**
	 * 保存对象
	 * @param object
	 */
	void save(Object object);
	
	/**
	 * 保存对象
	 * @param entity
	 */
	void save(EntityMap entity);
	
	/**
	 * 关闭session实例
	 */
	void close();
	
	void commit();
	void rollback();
}
