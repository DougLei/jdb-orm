package com.douglei.session;

import java.util.List;
import java.util.Map;

/**
 * 和数据库交互的sql session接口
 * @author DougLei
 */
public interface SqlSession {

	/**
	 * 执行查询
	 * @param sql
	 * @return
	 */
	List<Map<String, Object>> query(String sql);
	
	/**
	 * 执行查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return
	 */
	List<Map<String, Object>> query(String sql, List<Object> parameters);
	
	/**
	 * 关闭session实例
	 */
	void close();
	
	void commit();
	void rollback();
}
