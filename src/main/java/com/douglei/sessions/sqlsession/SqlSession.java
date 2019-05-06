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
	 * @return 返回<列名:值>的list-map集合
	 */
	List<Map<String, Object>> query(String sql);
	/**
	 * 执行批量查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<列名:值>的list-map集合
	 */
	List<Map<String, Object>> query(String sql, List<? extends Object> parameters);
	
	/**
	 * 执行唯一查询
	 * @param sql
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> uniqueQuery(String sql);
	/**
	 * 执行唯一查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> uniqueQuery(String sql, List<? extends Object> parameters);
	
	/**
	 * 执行批量查询
	 * @param sql
	 * @return 返回<值>的list-数组集合
	 */
	List<Object[]> query_(String sql);
	/**
	 * 执行批量查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<值>的list-数组集合
	 */
	List<Object[]> query_(String sql, List<? extends Object> parameters);
	
	/**
	 * 执行唯一查询
	 * @param sql
	 * @return 返回<值>的数组
	 */
	Object[] uniqueQuery_(String sql);
	/**
	 * 执行唯一查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<值>的数组
	 */
	Object[] uniqueQuery_(String sql, List<? extends Object> parameters);
	
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
	
	/**
	 * 查询结果集
	 * @param targetClass
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> List<T> query(Class<T> targetClass, String sql, List<Object> parameters);
	
	/**
	 * 查询唯一结果
	 * @param targetClass
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> T uniqueQuery(Class<T> targetClass, String sql, List<Object> parameters);
}
