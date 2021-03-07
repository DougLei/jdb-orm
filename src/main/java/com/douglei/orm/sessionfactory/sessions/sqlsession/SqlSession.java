package com.douglei.orm.sessionfactory.sessions.sqlsession;

import java.util.List;
import java.util.Map;

import com.douglei.orm.sql.query.page.PageResult;
import com.douglei.orm.sql.statement.AutoIncrementID;
import com.douglei.orm.sql.statement.InsertResult;

/**
 * 和数据库交互的sql session接口
 * @author DougLei
 */
public interface SqlSession {
	
	/**
	 * 查询
	 * @param sql
	 * @return 返回<列名:值>的list-map集合
	 */
	default List<Map<String, Object>> query(String sql) {
		return query(sql, null);
	}
	/**
	 * 查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<列名:值>的list-map集合
	 */
	List<Map<String, Object>> query(String sql, List<Object> parameters);
	
	/**
	 * 查询
	 * @param targetClass
	 * @param sql
	 * @return 
	 */
	<T> List<T> query(Class<T> targetClass, String sql);
	/**
	 * 查询
	 * @param targetClass
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 
	 */
	<T> List<T> query(Class<T> targetClass, String sql, List<Object> parameters);
	
	/**
	 * 查询
	 * @param sql
	 * @return 返回<值>的list-数组集合
	 */
	default List<Object[]> query_(String sql) {
		return query_(sql, null);
	}
	/**
	 * 查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<值>的list-数组集合
	 */
	List<Object[]> query_(String sql, List<Object> parameters);
	
	/**
	 * 唯一查询
	 * @param sql
	 * @return 返回<列名:值>的map集合
	 */
	default Map<String, Object> uniqueQuery(String sql) {
		return uniqueQuery(sql, null);
	}
	/**
	 * 唯一查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> uniqueQuery(String sql, List<Object> parameters);
	
	/**
	 * 唯一查询
	 * @param targetClass
	 * @param sql
	 * @return 
	 */
	<T> T uniqueQuery(Class<T> targetClass, String sql);
	/**
	 * 唯一查询
	 * @param targetClass
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 
	 */
	<T> T uniqueQuery(Class<T> targetClass, String sql, List<Object> parameters);
	
	/**
	 * 唯一查询
	 * @param sql
	 * @return 返回<值>的数组
	 */
	default Object[] uniqueQuery_(String sql) {
		return uniqueQuery_(sql, null);
	}
	/**
	 * 唯一查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<值>的数组
	 */
	Object[] uniqueQuery_(String sql, List<Object> parameters);
	
	/**
	 * 限制查询
	 * @param sql
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @return 返回<列名:值>的map集合
	 */
	default List<Map<String, Object>> limitQuery(String sql, int startRow, int length) {
		return limitQuery(sql, startRow, length, null);
	}
	/**
	 * 限制查询
	 * @param sql
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param parameters 传入的参数
	 * @return 返回<列名:值>的map集合
	 */
	List<Map<String, Object>> limitQuery(String sql, int startRow, int length, List<Object> parameters);
	
	/**
	 * 限制查询
	 * @param targetClass
	 * @param sql
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @return 
	 */
	<T> List<T> limitQuery(Class<T> targetClass, String sql, int startRow, int length);
	/**
	 * 限制查询
	 * @param targetClass
	 * @param sql
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param parameters 传入的参数
	 * @return 
	 */
	<T> List<T> limitQuery(Class<T> targetClass, String sql, int startRow, int length, List<Object> parameters);
	
	/**
	 * 限制查询
	 * @param sql
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @return 返回<值>的数组
	 */
	default List<Object[]> limitQuery_(String sql, int startRow, int length) {
		return limitQuery_(sql, startRow, length, null);
	}
	/**
	 * 限制查询
	 * @param sql
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param parameters 传入的参数
	 * @return 返回<值>的数组
	 */
	List<Object[]> limitQuery_(String sql, int startRow, int length, List<Object> parameters);
	
	/**
	 * 总数量查询
	 * @param sql
	 * @return
	 */
	default long countQuery(String sql) {
		return countQuery(sql, null);
	}
	/**
	 * 总数量查询
	 * @param sql
	 * @param parameters
	 * @return
	 */
	long countQuery(String sql, List<Object> parameters);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @return
	 */
	default PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String sql) {
		return pageQuery(pageNum, pageSize, sql, null);
	}
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
	 * 插入数据
	 * @param sql
	 * @param autoIncrementID
	 * @return 
	 */
	default InsertResult executeInsert(String sql, AutoIncrementID autoIncrementID) {
		return executeInsert(sql, null, autoIncrementID);
	}
	/**
	 * 插入数据
	 * @param sql
	 * @param parameters
	 * @param autoIncrementID
	 * @return 
	 */
	InsertResult executeInsert(String sql, List<Object> parameters, AutoIncrementID autoIncrementID);
	
	/**
	 * 增删改数据
	 * @param sql
	 * @return 返回影响的行数
	 */
	default int executeUpdate(String sql) {
		return executeUpdate(sql, null);
	}
	/**
	 * 增删改数据
	 * @param sql
	 * @param parameters
	 * @return 返回影响的行数
	 */
	int executeUpdate(String sql, List<Object> parameters);
	
	/**
	 * 执行存储过程
	 * @param procedureExecutor
	 * @return
	 */
	Object executeProcedure(ProcedureExecutor procedureExecutor);
}
