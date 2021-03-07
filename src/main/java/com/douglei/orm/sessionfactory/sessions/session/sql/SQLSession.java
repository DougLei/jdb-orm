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
	 * @return 返回<列名:值>的list-map集合
	 */
	default List<Map<String, Object>> query(String namespace, String name) {
		return query(namespace, name, null);
	}
	
	/**
	 * 查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<列名:值>的list-map集合
	 */
	List<Map<String, Object>> query(String namespace, String name, Object sqlParameter);
	
	/**
	 * 查询
	 * @param targetClass
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 返回<列名:值>的list-map集合
	 */
	default <T> List<T> query(Class<T> targetClass, String namespace, String name) {
		return query(targetClass, namespace, name, null);
	}
	/**
	 * 查询
	 * @param targetClass
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<列名:值>的list-map集合
	 */
	<T> List<T> query(Class<T> targetClass, String namespace, String name, Object sqlParameter);
	
	/**
	 * 查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 返回<值>的list-数组集合
	 */
	default List<Object[]> query_(String namespace, String name) {
		return query_(namespace, name, null);
	}
	/**
	 * 查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<值>的list-数组集合
	 */
	List<Object[]> query_(String namespace, String name, Object sqlParameter);
	
	/**
	 * 唯一查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 返回<列名:值>的map集合
	 */
	default Map<String, Object> uniqueQuery(String namespace, String name) {
		return uniqueQuery(namespace, name, null);
	}
	/**
	 * 唯一查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> uniqueQuery(String namespace, String name, Object sqlParameter);
	
	/**
	 * 唯一查询
	 * @param targetClass
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 返回<列名:值>的map集合
	 */
	default <T> T uniqueQuery(Class<T> targetClass, String namespace, String name) {
		return uniqueQuery(targetClass, namespace, name, null);
	}
	/**
	 * 唯一查询
	 * @param targetClass
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<列名:值>的map集合
	 */
	<T> T uniqueQuery(Class<T> targetClass, String namespace, String name, Object sqlParameter);
	
	/**
	 * 唯一查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 返回<值>的数组
	 */
	default Object[] uniqueQuery_(String namespace, String name) {
		return uniqueQuery_(namespace, name, null);
	}
	/**
	 * 唯一查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<值>的数组
	 */
	Object[] uniqueQuery_(String namespace, String name, Object sqlParameter);
	
	/**
	 * 限制查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @return 返回<列名:值>的map集合
	 */
	default List<Map<String, Object>> limitQuery(String namespace, String name, int startRow, int length) {
		return limitQuery(namespace, name, startRow, length, null);
	}
	/**
	 * 限制查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param sqlParameter
	 * @return 返回<列名:值>的map集合
	 */
	List<Map<String, Object>> limitQuery(String namespace, String name, int startRow, int length, Object sqlParameter);
	
	/**
	 * 限制查询
	 * @param targetClass
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @return 
	 */
	default <T> List<T> limitQuery(Class<T> targetClass, String namespace, String name, int startRow, int length) {
		return limitQuery(targetClass, namespace, name, startRow, length, null);
	}
	/**
	 * 限制查询
	 * @param targetClass
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param sqlParameter
	 * @return 
	 */
	<T> List<T> limitQuery(Class<T> targetClass, String namespace, String name, int startRow, int length, Object sqlParameter);
	
	/**
	 * 限制查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param sqlParameter
	 * @return 返回<值>的数组
	 */
	default List<Object[]> limitQuery_(String namespace, String name, int startRow, int length) {
		return limitQuery_(namespace, name, startRow, length, null);
	}
	/**
	 * 限制查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param sqlParameter
	 * @return 返回<值>的数组
	 */
	List<Object[]> limitQuery_(String namespace, String name, int startRow, int length, Object sqlParameter);
	
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
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return
	 */
	default <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String namespace, String name) {
		return pageQuery(targetClass, pageNum, pageSize, namespace, name, null);
	}
	/**
	 * 分页查询
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String namespace, String name, Object sqlParameter);
	
	/**
	 * 增删改数据
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的所有sql; 如果其中出现非{@link ContentType INSERT/DELETE/UPDATE/DECLARE}类型的sql语句, 则会抛出异常
	 * @return 影响的行数
	 */
	int executeUpdate(String namespace, String name);
	/**
	 * 增删改数据
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的所有sql; 如果其中出现非{@link ContentType INSERT/DELETE/UPDATE/DECLARE}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 影响的行数
	 */
	int executeUpdate(String namespace, String name, Object sqlParameter);
	/**
	 * 批量增删改数据
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的所有sql; 如果其中出现非{@link ContentType INSERT/DELETE/UPDATE/DECLARE}类型的sql语句, 则会抛出异常
	 * @param sqlParameters
	 * @return 影响的行数
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
