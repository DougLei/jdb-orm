package com.douglei.orm.sessionfactory.sessions.session.sql;

import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.sql.query.page.PageResult;

/**
 * 和数据库交互的session接口
 * @author DougLei
 */
public interface SQLSession {
	
	/**
	 * 查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 
	 */
	default List<Map<String, Object>> query(String namespace, String name) {
		return query(namespace, name, null);
	}
	
	/**
	 * 查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 
	 */
	List<Map<String, Object>> query(String namespace, String name, Object sqlParameter);
	
	/**
	 * 查询
	 * @param clazz
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 
	 */
	default <T> List<T> query(Class<T> clazz, String namespace, String name) {
		return query(clazz, namespace, name, null);
	}
	/**
	 * 查询
	 * @param clazz
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 
	 */
	<T> List<T> query(Class<T> clazz, String namespace, String name, Object sqlParameter);
	
	/**
	 * 查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 
	 */
	default List<Object[]> query_(String namespace, String name) {
		return query_(namespace, name, null);
	}
	/**
	 * 查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 
	 */
	List<Object[]> query_(String namespace, String name, Object sqlParameter);
	
	/**
	 * 唯一查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 
	 */
	default Map<String, Object> uniqueQuery(String namespace, String name) {
		return uniqueQuery(namespace, name, null);
	}
	/**
	 * 唯一查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 
	 */
	Map<String, Object> uniqueQuery(String namespace, String name, Object sqlParameter);
	
	/**
	 * 唯一查询
	 * @param clazz
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 
	 */
	default <T> T uniqueQuery(Class<T> clazz, String namespace, String name) {
		return uniqueQuery(clazz, namespace, name, null);
	}
	/**
	 * 唯一查询
	 * @param clazz
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 
	 */
	<T> T uniqueQuery(Class<T> clazz, String namespace, String name, Object sqlParameter);
	
	/**
	 * 唯一查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 
	 */
	default Object[] uniqueQuery_(String namespace, String name) {
		return uniqueQuery_(namespace, name, null);
	}
	/**
	 * 唯一查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 
	 */
	Object[] uniqueQuery_(String namespace, String name, Object sqlParameter);
	
	/**
	 * 限制查询
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 
	 */
	default List<Map<String, Object>> limitQuery(int startRow, int length, String namespace, String name) {
		return limitQuery(startRow, length, namespace, name, null);
	}
	/**
	 * 限制查询
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 
	 */
	List<Map<String, Object>> limitQuery(int startRow, int length, String namespace, String name, Object sqlParameter);
	
	/**
	 * 限制查询
	 * @param clazz
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 
	 */
	default <T> List<T> limitQuery(Class<T> clazz, int startRow, int length, String namespace, String name) {
		return limitQuery(clazz, startRow, length, namespace, name, null);
	}
	/**
	 * 限制查询
	 * @param clazz
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 
	 */
	<T> List<T> limitQuery(Class<T> clazz, int startRow, int length, String namespace, String name, Object sqlParameter);
	
	/**
	 * 限制查询
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 
	 */
	default List<Object[]> limitQuery_(int startRow, int length, String namespace, String name) {
		return limitQuery_(startRow, length, namespace, name, null);
	}
	/**
	 * 限制查询
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 
	 */
	List<Object[]> limitQuery_(int startRow, int length, String namespace, String name, Object sqlParameter);
	
	/**
	 * 总数量查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return
	 */
	default long countQuery(String namespace, String name) {
		return countQuery(namespace, name, null);
	}
	
	/**
	 * 总数量查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return
	 */
	long countQuery(String namespace, String name, Object sqlParameter);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return
	 */
	default PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String namespace, String name) {
		return pageQuery(pageNum, pageSize, namespace, name, null);
	}
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return
	 */
	PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String namespace, String name, Object sqlParameter);
	
	/**
	 * 分页查询
	 * @param clazz
	 * @param pageNum
	 * @param pageSize
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return
	 */
	default <T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, String namespace, String name) {
		return pageQuery(clazz, pageNum, pageSize, namespace, name, null);
	}
	/**
	 * 分页查询
	 * @param clazz
	 * @param pageNum
	 * @param pageSize
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, String namespace, String name, Object sqlParameter);
	
	/**
	 * 增删改数据
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的所有sql; 如果其中出现非{@link ContentType INSERT/DELETE/UPDATE/DECLARE}类型的sql语句, 则会抛出异常
	 * @return 
	 */
	int executeUpdate(String namespace, String name);
	/**
	 * 增删改数据
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的所有sql; 如果其中出现非{@link ContentType INSERT/DELETE/UPDATE/DECLARE}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 
	 */
	int executeUpdate(String namespace, String name, Object sqlParameter);
	/**
	 * 批量增删改数据
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的所有sql; 如果其中出现非{@link ContentType INSERT/DELETE/UPDATE/DECLARE}类型的sql语句, 则会抛出异常
	 * @param sqlParameters
	 * @return 
	 */
	int executeUpdates(String namespace, String name, List<? extends Object> sqlParameters);
	
	/**
	 * 执行存储过程
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType PROCEDURE}类型的sql语句, 则会抛出异常
	 * @return Map<输出参数名 : 输出值>, 没有输出值时, 返回null
	 */
	default Object executeProcedure(String namespace, String name) {
		return executeProcedure(namespace, name, null);
	}
	/**
	 * 执行存储过程
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType PROCEDURE}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return Map<输出参数名 : 输出值>, 没有输出值时, 返回null
	 */
	Object executeProcedure(String namespace, String name, Object sqlParameter);
	/**
	 * 批量执行存储过程
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType PROCEDURE}类型的sql语句, 则会抛出异常
	 * @param sqlParameters
	 * @return List<Map<输出参数名 : 输出值>>, 没有输出值时, 返回null
	 */
	List<Object> executeProcedures(String namespace, String name, List<? extends Object> sqlParameters);
}
