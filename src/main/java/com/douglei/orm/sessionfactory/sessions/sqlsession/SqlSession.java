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
	 * @param clazz
	 * @param sql
	 * @return 
	 */
	<T> List<T> query(Class<T> clazz, String sql);
	/**
	 * 查询
	 * @param clazz
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 
	 */
	<T> List<T> query(Class<T> clazz, String sql, List<Object> parameters);
	
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
	 * @param clazz
	 * @param sql
	 * @return 
	 */
	<T> T uniqueQuery(Class<T> clazz, String sql);
	/**
	 * 唯一查询
	 * @param clazz
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 
	 */
	<T> T uniqueQuery(Class<T> clazz, String sql, List<Object> parameters);
	
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
	 * @param startRow 起始的行数, 值从1开始
	 * @param length 查询的数据长度, -1表示不限制长度
	 * @param sql
	 * @return 返回<列名:值>的map集合
	 */
	default List<Map<String, Object>> limitQuery(int startRow, int length, String sql) {
		return limitQuery(startRow, length, sql, null);
	}
	/**
	 * 限制查询
	 * @param startRow 起始的行数, 值从1开始
	 * @param length 查询的数据长度, -1表示不限制长度
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<列名:值>的map集合
	 */
	List<Map<String, Object>> limitQuery(int startRow, int length, String sql, List<Object> parameters);
	
	/**
	 * 限制查询
	 * @param clazz
	 * @param startRow 起始的行数, 值从1开始
	 * @param length 查询的数据长度, -1表示不限制长度
	 * @param sql
	 * @return 
	 */
	<T> List<T> limitQuery(Class<T> clazz, int startRow, int length, String sql);
	/**
	 * 限制查询
	 * @param clazz
	 * @param startRow 起始的行数, 值从1开始
	 * @param length 查询的数据长度, -1表示不限制长度
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 
	 */
	<T> List<T> limitQuery(Class<T> clazz, int startRow, int length, String sql, List<Object> parameters);
	
	/**
	 * 限制查询
	 * @param startRow 起始的行数, 值从1开始
	 * @param length 查询的数据长度, -1表示不限制长度
	 * @param sql
	 * @return 返回<值>的数组
	 */
	default List<Object[]> limitQuery_(int startRow, int length, String sql) {
		return limitQuery_(startRow, length, sql, null);
	}
	/**
	 * 限制查询
	 * @param startRow 起始的行数, 值从1开始
	 * @param length 查询的数据长度, -1表示不限制长度
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<值>的数组
	 */
	List<Object[]> limitQuery_(int startRow, int length, String sql, List<Object> parameters);
	
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
	 * @param clazz
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, String sql);
	/**
	 * 分页查询
	 * @param clazz
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, String sql, List<Object> parameters);
	
	/**
	 * 递归查询
	 * @param entity
	 * @param sql
	 * @return
	 */
	default List<Map<String, Object>> recursiveQuery(RecursiveEntity entity, String sql){
		return recursiveQuery(entity, sql, null);
	}
	/**
	 * 递归查询
	 * @param entity
	 * @param sql
	 * @param parameters
	 * @return
	 */
	List<Map<String, Object>> recursiveQuery(RecursiveEntity entity, String sql, List<Object> parameters);
	
	/**
	 * 递归查询
	 * @param clazz
	 * @param entity
	 * @param sql
	 * @return
	 */
	<T> List<T> recursiveQuery(Class<T> clazz, RecursiveEntity entity, String sql);
	/**
	 * 递归查询
	 * @param clazz
	 * @param entity
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> List<T> recursiveQuery(Class<T> clazz, RecursiveEntity entity, String sql, List<Object> parameters);
	
	/**
	 * 分页递归查询
	 * @param entity
	 * @param sql
	 * @return
	 */
	default PageResult<Map<String, Object>> pageRecursiveQuery(PageRecursiveEntity entity, String sql){
		return pageRecursiveQuery(entity, sql, null);
	}
	/**
	 * 分页递归查询
	 * @param entity
	 * @param sql
	 * @param parameters
	 * @return
	 */
	PageResult<Map<String, Object>> pageRecursiveQuery(PageRecursiveEntity entity, String sql, List<Object> parameters);
	
	/**
	 * 分页递归查询
	 * @param clazz
	 * @param entity
	 * @param sql
	 * @return
	 */
	<T> PageResult<T> pageRecursiveQuery(Class<T> clazz, PageRecursiveEntity entity, String sql);
	/**
	 * 分页递归查询
	 * @param clazz
	 * @param entity
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> PageResult<T> pageRecursiveQuery(Class<T> clazz, PageRecursiveEntity entity, String sql, List<Object> parameters);
	
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
