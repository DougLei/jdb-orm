package com.douglei.sessions.sqlsession;

import java.util.List;
import java.util.Map;

import com.douglei.core.sql.pagequery.PageResult;

/**
 * 和数据库交互的sql session接口
 * @author DougLei
 */
public interface SqlSession {
	
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
	List<Map<String, Object>> query(String sql, List<Object> parameters);
	
	/**
	 * 执行批量查询
	 * @param targetClass
	 * @param sql
	 * @return 
	 */
	<T> List<T> query(Class<T> targetClass, String sql);
	/**
	 * 执行批量查询
	 * @param targetClass
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 
	 */
	<T> List<T> query(Class<T> targetClass, String sql, List<Object> parameters);
	
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
	Map<String, Object> uniqueQuery(String sql, List<Object> parameters);
	
	/**
	 * 执行唯一查询
	 * @param targetClass
	 * @param sql
	 * @return 
	 */
	<T> T uniqueQuery(Class<T> targetClass, String sql);
	/**
	 * 执行唯一查询
	 * @param targetClass
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 
	 */
	<T> T uniqueQuery(Class<T> targetClass, String sql, List<Object> parameters);
	
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
	List<Object[]> query_(String sql, List<Object> parameters);
	
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
	Object[] uniqueQuery_(String sql, List<Object> parameters);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @return
	 */
	PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String sql);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @param parameters
	 * @return
	 */
	PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String sql, List<Object> parameters);
	
	/**
	 * 分页查询
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String sql);
	
	/**
	 * 分页查询
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String sql, List<Object> parameters);
	
	/**
	 * 执行增删改查操作
	 * @param sql
	 * @return
	 */
	int executeUpdate(String sql);
	/**
	 * 执行增删改查操作
	 * @param sql
	 * @param parameters
	 * @return
	 */
	int executeUpdate(String sql, List<Object> parameters);
	
	/**
	 * 执行存储过程
	 * @param procedureExecutor
	 * @return
	 */
	Object executeProcedure(ProcedureExecutor procedureExecutor);
}
