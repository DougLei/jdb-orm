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
	 * @param code
	 * @return 返回<列名:值>的list-map集合
	 */
	List<Map<String, Object>> query(String code);
	/**
	 * 执行批量查询
	 * @param code
	 * @param parameters 传入的参数
	 * @return 返回<列名:值>的list-map集合
	 */
	List<Map<String, Object>> query(String code, Map<String, Object> parameters);
	
	/**
	 * 执行唯一查询
	 * @param code
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> uniqueQuery(String code);
	/**
	 * 执行唯一查询
	 * @param code
	 * @param parameters 传入的参数
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> uniqueQuery(String code, Map<String, Object> parameters);
	
	/**
	 * 执行批量查询
	 * @param code
	 * @return 返回<值>的list-数组集合
	 */
	List<Object[]> query_(String code);
	/**
	 * 执行批量查询
	 * @param code
	 * @param parameters 传入的参数
	 * @return 返回<值>的list-数组集合
	 */
	List<Object[]> query_(String code, Map<String, Object> parameters);
	
	/**
	 * 执行唯一查询
	 * @param code
	 * @return 返回<值>的数组
	 */
	Object[] uniqueQuery_(String code);
	/**
	 * 执行唯一查询
	 * @param code
	 * @param parameters 传入的参数
	 * @return 返回<值>的数组
	 */
	Object[] uniqueQuery_(String code, Map<String, Object> parameters);
	
	/**
	 * 执行增删改查操作
	 * @param code
	 * @return
	 */
	int executeUpdate(String code);
	/**
	 * 执行增删改查操作
	 * @param code
	 * @param parameters
	 * @return
	 */
	int executeUpdate(String code, Map<String, Object> parameters);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param code
	 * @return
	 */
	PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String code);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param code
	 * @param parameters
	 * @return
	 */
	PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String code, Map<String, Object> parameters);
}
