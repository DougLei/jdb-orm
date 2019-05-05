package com.douglei.sessions.sqlsession;

import java.util.List;
import java.util.Map;

import com.douglei.sessions.BasicSession;

/**
 * 和数据库交互的sql session接口
 * @author DougLei
 */
public interface SqlSession extends BasicSession{
	
	/**
	 * 执行批量查询
	 * @param sql
	 * @return
	 */
	List<Map<String, Object>> query(String sql);
	/**
	 * 执行批量查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return
	 */
	List<Map<String, Object>> query(String sql, List<Object> parameters);
	/**
	 * 执行批量查询
	 * @param clz
	 * @param sql
	 * @param parameters 传入的参数
	 * @return
	 */
	<T> List<T> query(Class<T> clz, String sql, List<Object> parameters);
	
	/**
	 * 执行唯一查询
	 * @param sql
	 * @return
	 */
	Map<String, Object> uniqueQuery(String sql);
	/**
	 * 执行唯一查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return
	 */
	Map<String, Object> uniqueQuery(String sql, List<Object> parameters);
	/**
	 * 执行唯一查询
	 * @param clz
	 * @param sql
	 * @param parameters 传入的参数
	 * @return
	 */
	<T> T uniqueQuery(Class<T> clz, String sql, List<Object> parameters);
	
	/**
	 * 执行增删改查操作
	 * @param sql
	 * @return
	 */
	int executeUpdate(String sql);
	/**
	 * 执行增删改查操作
	 * @param string
	 * @param parameters
	 * @return
	 */
	int executeUpdate(String sql, List<? extends Object> parameters);
}
