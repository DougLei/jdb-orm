package com.douglei.sessions.session.sql;

import java.util.List;
import java.util.Map;

import com.douglei.database.sql.pagequery.PageResult;
import com.douglei.sessions.BasicSession;

/**
 * 和数据库交互的session接口
 * @author DougLei
 */
public interface SQLSession extends BasicSession{
	
	/**
	 * 执行批量查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return 返回<列名:值>的list-map集合
	 */
	List<Map<String, Object>> query(String namespace, String name);
	/**
	 * 执行批量查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameterMap
	 * @return 返回<列名:值>的list-map集合
	 */
	List<Map<String, Object>> query(String namespace, String name, Map<String, Object> sqlParameterMap);
	
	/**
	 * 执行唯一查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> uniqueQuery(String namespace, String name);
	/**
	 * 执行唯一查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameterMap
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> uniqueQuery(String namespace, String name, Map<String, Object> sqlParameterMap);
	
	/**
	 * 执行批量查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return 返回<值>的list-数组集合
	 */
	List<Object[]> query_(String namespace, String name);
	/**
	 * 执行批量查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameterMap
	 * @return 返回<值>的list-数组集合
	 */
	List<Object[]> query_(String namespace, String name, Map<String, Object> sqlParameterMap);
	
	/**
	 * 执行唯一查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return 返回<值>的数组
	 */
	Object[] uniqueQuery_(String namespace, String name);
	/**
	 * 执行唯一查询
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameterMap
	 * @return 返回<值>的数组
	 */
	Object[] uniqueQuery_(String namespace, String name, Map<String, Object> sqlParameterMap);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return
	 */
	PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String namespace, String name);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameterMap
	 * @return
	 */
	PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String namespace, String name, Map<String, Object> sqlParameterMap);
	
	/**
	 * 执行增删改查操作
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @return
	 */
	int executeUpdate(String namespace, String name);
	/**
	 * 执行增删改查操作
	 * @param namespace <sql>元素中的namespace属性值, 如果没有, 传入null
	 * @param name <sql>元素中的name属性值, 不能为空
	 * @param sqlParameterMap
	 * @return
	 */
	int executeUpdate(String namespace, String name, Map<String, Object> sqlParameterMap);
	
	/**
	 * 执行存储过程
	 * @param namespace
	 * @param name
	 * @return
	 */
	Object executeProcedure(String namespace, String name);
	
	/**
	 * 执行存储过程
	 * @param namespace
	 * @param name
	 * @param sqlParameterMap
	 * @return
	 */
	Object executeProcedure(String namespace, String name, Map<String, Object> sqlParameterMap);
}
