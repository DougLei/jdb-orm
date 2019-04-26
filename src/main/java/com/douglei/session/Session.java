package com.douglei.session;

import java.util.Map;

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
	 * @param entityName
	 * @param map
	 */
	void save(String entityName, Map<String, Object> map);
	
	/**
	 * 关闭session实例
	 */
	void close();
	
	void commit();
	void rollback();
}
