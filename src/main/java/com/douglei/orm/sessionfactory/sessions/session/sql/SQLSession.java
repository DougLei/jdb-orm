package com.douglei.orm.sessionfactory.sessions.session.sql;

import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.sql.pagequery.PageResult;

/**
 * 和数据库交互的session接口
 * @author DougLei
 */
public interface SQLSession {
	public static final String PROCEDURE_DIRECTLY_RETURN_RESULTSET_NAME_PREFIX = "_procedure_resultset_";
	
	/**
	 * 执行批量查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 返回<列名:值>的list-map集合
	 */
	default List<Map<String, Object>> query(String namespace, String name) {
		return query(namespace, name, null);
	}
	
	/**
	 * 执行批量查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<列名:值>的list-map集合
	 */
	List<Map<String, Object>> query(String namespace, String name, Object sqlParameter);
	
	/**
	 * 执行批量查询
	 * @param targetClass
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 返回<列名:值>的list-map集合
	 */
	default <T> List<T> query(Class<T> targetClass, String namespace, String name) {
		return query(targetClass, namespace, name, null);
	}
	/**
	 * 执行批量查询
	 * @param targetClass
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<列名:值>的list-map集合
	 */
	<T> List<T> query(Class<T> targetClass, String namespace, String name, Object sqlParameter);
	
	/**
	 * 执行批量查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 返回<值>的list-数组集合
	 */
	default List<Object[]> query_(String namespace, String name) {
		return query_(namespace, name, null);
	}
	/**
	 * 执行批量查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<值>的list-数组集合
	 */
	List<Object[]> query_(String namespace, String name, Object sqlParameter);
	
	/**
	 * 执行唯一查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 返回<列名:值>的map集合
	 */
	default Map<String, Object> uniqueQuery(String namespace, String name) {
		return uniqueQuery(namespace, name, null);
	}
	/**
	 * 执行唯一查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> uniqueQuery(String namespace, String name, Object sqlParameter);
	
	/**
	 * 执行唯一查询
	 * @param targetClass
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 返回<列名:值>的map集合
	 */
	default <T> T uniqueQuery(Class<T> targetClass, String namespace, String name) {
		return uniqueQuery(targetClass, namespace, name, null);
	}
	/**
	 * 执行唯一查询
	 * @param targetClass
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<列名:值>的map集合
	 */
	<T> T uniqueQuery(Class<T> targetClass, String namespace, String name, Object sqlParameter);
	
	/**
	 * 执行唯一查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 返回<值>的数组
	 */
	default Object[] uniqueQuery_(String namespace, String name) {
		return uniqueQuery_(namespace, name, null);
	}
	/**
	 * 执行唯一查询
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<值>的数组
	 */
	Object[] uniqueQuery_(String namespace, String name, Object sqlParameter);
	
	/**
	 * 查询第一条数据
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 返回<列名:值>的map集合
	 */
	default Map<String, Object> queryFirst(String namespace, String name) {
		return queryFirst(namespace, name, null);
	}
	/**
	 * 查询第一条数据
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> queryFirst(String namespace, String name, Object sqlParameter);
	
	/**
	 * 查询第一条数据
	 * @param targetClass
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return 
	 */
	default <T> T queryFirst(Class<T> targetClass, String namespace, String name) {
		return queryFirst(targetClass, namespace, namespace, null);
	}
	/**
	 * 查询第一条数据
	 * @param targetClass
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 
	 */
	<T> T queryFirst(Class<T> targetClass, String namespace, String name, Object sqlParameter);
	
	/**
	 * 查询第一条数据
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<值>的数组
	 */
	default Object[] queryFirst_(String namespace, String name) {
		return queryFirst_(namespace, name, null);
	}
	/**
	 * 查询第一条数据
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 返回<值>的数组
	 */
	Object[] queryFirst_(String namespace, String name, Object sqlParameter);
	
	/**
	 * 查询结果数据总数量
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return
	 */
	default long countQuery(String namespace, String name) {
		return countQuery(namespace, name, null);
	}
	
	/**
	 * 查询结果数据总数量
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
	 * 递归查询
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return
	 */
	default List<Map<String, Object>> recursiveQuery(int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String namespace, String name){
		return recursiveQuery(deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, namespace, namespace, null);
	}
	/**
	 * 递归查询
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return
	 */
	List<Map<String, Object>> recursiveQuery(int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String namespace, String name, Object sqlParameter);
	
	/**
	 * 递归查询
	 * @param targetClass
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 传入对应targetClass中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return
	 */
	default <T> List<T> recursiveQuery(Class<T> targetClass, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String namespace, String name){
		return recursiveQuery(targetClass, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, namespace, namespace, null);
	}
	/**
	 * 递归查询
	 * @param targetClass
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 传入对应targetClass中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return
	 */
	<T> List<T> recursiveQuery(Class<T> targetClass, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String namespace, String name, Object sqlParameter);
	
	/**
	 * 分页递归查询, 只对根数据(即第一层的数据)进行分页
	 * @param pageNum
	 * @param pageSize
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return
	 */
	default PageResult<Map<String, Object>> pageRecursiveQuery(int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String namespace, String name){
		return pageRecursiveQuery(pageNum, pageSize, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, namespace, name, null);
	}
	/**
	 * 分页递归查询, 只对根数据(即第一层的数据)进行分页
	 * @param pageNum
	 * @param pageSize
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return
	 */
	PageResult<Map<String, Object>> pageRecursiveQuery(int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String namespace, String name, Object sqlParameter);
	
	/**
	 * 分页递归查询, 只对根数据(即第一层的数据)进行分页
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 传入对应targetClass中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @return
	 */
	default <T> PageResult<T> pageRecursiveQuery(Class<T> targetClass, int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String namespace, String name){
		return pageRecursiveQuery(targetClass, pageNum, pageSize, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, namespace, namespace, null);
	}
	/**
	 * 分页递归查询, 只对根数据(即第一层的数据)进行分页
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 传入对应targetClass中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的第一个sql; 如果其非{@link ContentType SELECT}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return
	 */
	<T> PageResult<T> pageRecursiveQuery(Class<T> targetClass, int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String namespace, String name, Object sqlParameter);
	
	
	/**
	 * 执行增删改查操作
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的所有sql; 如果其中出现非{@link ContentType INSERT/DELETE/UPDATE/DECLARE}类型的sql语句, 则会抛出异常
	 * @return 影响的行数
	 */
	int executeUpdate(String namespace, String name);
	/**
	 * 执行增删改查操作
	 * @param namespace sql元素中的namespace属性值, 不能为空
	 * @param name sql元素中的name属性值, 如果传入null, 则表示调用该namespace资源下的所有sql; 如果其中出现非{@link ContentType INSERT/DELETE/UPDATE/DECLARE}类型的sql语句, 则会抛出异常
	 * @param sqlParameter
	 * @return 影响的行数
	 */
	int executeUpdate(String namespace, String name, Object sqlParameter);
	/**
	 * 批量执行增删改查操作
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
