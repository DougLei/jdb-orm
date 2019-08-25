package com.douglei.orm.sessionfactory.sessions.session.sql;

import java.util.List;
import java.util.Map;

import com.douglei.orm.core.sql.pagequery.PageResult;

/**
 * 和数据库交互的session接口
 * @author DougLei
 */
public interface SQLSession {
	public static final String PROCEDURE_DIRECTLY_RETURN_RESULTSET_NAME_PREFIX = "_procedure_resultset_";
	
	/**
	 * 执行批量查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return 返回<列名:值>的list-map集合
	 */
	default List<Map<String, Object>> query(String namespace, String name) {
		return query(namespace, name, null);
	}
	
	/**
	 * 执行批量查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameter
	 * @return 返回<列名:值>的list-map集合
	 */
	List<Map<String, Object>> query(String namespace, String name, Object sqlParameter);
	
	/**
	 * 执行批量查询
	 * @param targetClass
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return 返回<列名:值>的list-map集合
	 */
	default <T> List<T> query(Class<T> targetClass, String namespace, String name) {
		return query(targetClass, namespace, name, null);
	}
	/**
	 * 执行批量查询
	 * @param targetClass
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameter
	 * @return 返回<列名:值>的list-map集合
	 */
	<T> List<T> query(Class<T> targetClass, String namespace, String name, Object sqlParameter);
	
	/**
	 * 执行唯一查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return 返回<列名:值>的map集合
	 */
	default Map<String, Object> uniqueQuery(String namespace, String name) {
		return uniqueQuery(namespace, name, null);
	}
	/**
	 * 执行唯一查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameter
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> uniqueQuery(String namespace, String name, Object sqlParameter);
	
	/**
	 * 执行唯一查询
	 * @param targetClass
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return 返回<列名:值>的map集合
	 */
	default <T> T uniqueQuery(Class<T> targetClass, String namespace, String name) {
		return uniqueQuery(targetClass, namespace, name, null);
	}
	/**
	 * 执行唯一查询
	 * @param targetClass
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameter
	 * @return 返回<列名:值>的map集合
	 */
	<T> T uniqueQuery(Class<T> targetClass, String namespace, String name, Object sqlParameter);
	
	/**
	 * 执行批量查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return 返回<值>的list-数组集合
	 */
	default List<Object[]> query_(String namespace, String name) {
		return query_(namespace, name, null);
	}
	/**
	 * 执行批量查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameter
	 * @return 返回<值>的list-数组集合
	 */
	List<Object[]> query_(String namespace, String name, Object sqlParameter);
	
	/**
	 * 执行唯一查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return 返回<值>的数组
	 */
	default Object[] uniqueQuery_(String namespace, String name) {
		return uniqueQuery_(namespace, name, null);
	}
	/**
	 * 执行唯一查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameter
	 * @return 返回<值>的数组
	 */
	Object[] uniqueQuery_(String namespace, String name, Object sqlParameter);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return
	 */
	default PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String namespace, String name) {
		return pageQuery(pageNum, pageSize, namespace, name, null);
	}
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameter
	 * @return
	 */
	PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String namespace, String name, Object sqlParameter);
	
	/**
	 * 分页查询
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
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
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameter
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String namespace, String name, Object sqlParameter);
	
	/**
	 * 获取要执行的sql
	 * @param namespace
	 * @param name
	 * @return
	 */
	default ExecutionSql getExecuteSql(String namespace, String name) {
		return getExecuteSql(namespace, name, null);
	}
	
	/**
	 * 获取要执行的sql
	 * @param namespace
	 * @param name
	 * @param sqlParameter
	 * @return
	 */
	ExecutionSql getExecuteSql(String namespace, String name, Object sqlParameter);
	
	/**
	 * 执行增删改查操作
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return
	 */
	default int executeUpdate(String namespace, String name) {
		return executeUpdate(namespace, name, null);
	}
	/**
	 * 执行增删改查操作
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameter
	 * @return
	 */
	int executeUpdate(String namespace, String name, Object sqlParameter);
	/**
	 * 执行增删改查操作
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameters
	 * @return
	 */
	int executeUpdate(String namespace, String name, List<Object> sqlParameters);
	
	/**
	 * 执行存储过程
	 * @param namespace
	 * @param name
	 * @return Map<输出参数名 : 输出值> 或 List<Map<输出参数名 : 输出值>>, 没有输出值时, 返回null
	 */
	default Object executeProcedure(String namespace, String name) {
		return executeProcedure(namespace, name, null);
	}
	/**
	 * 执行存储过程
	 * @param namespace
	 * @param name
	 * @param sqlParameter
	 * @return Map<输出参数名 : 输出值> 或 List<Map<输出参数名 : 输出值>>, 没有输出值时, 返回null
	 */
	Object executeProcedure(String namespace, String name, Object sqlParameter);
	/**
	 * 执行存储过程
	 * @param namespace
	 * @param name
	 * @param sqlParameters
	 * @return Map<输出参数名 : 输出值> 或 List<Map<输出参数名 : 输出值>>, 没有输出值时, 返回null
	 */
	List<Object> executeProcedure(String namespace, String name, List<Object> sqlParameters);
}
