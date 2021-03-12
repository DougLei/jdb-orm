package com.douglei.orm.sessionfactory.sessions.session.sqlquery;

import java.util.List;
import java.util.Map;

import com.douglei.orm.sql.query.page.PageResult;

/**
 * 
 * @author DougLei
 */
public interface SQLQuerySession {
	
	/**
	 * 查询
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> query(SQLQueryParameter parameter);
	/**
	 * 查询
	 * @param clazz
	 * @param parameter
	 * @return
	 */
	<T> List<T> query(Class<T> clazz, SQLQueryParameter parameter);
	/**
	 * 查询
	 * @param parameter
	 * @return
	 */
	List<Object[]> query_(SQLQueryParameter parameter);
	
	/**
	 * 唯一查询
	 * @param parameter
	 * @return
	 */
	Map<String, Object> uniqueQuery(SQLQueryParameter parameter);
	/**
	 * 唯一查询
	 * @param clazz
	 * @param parameter
	 * @return
	 */
	<T> T uniqueQuery(Class<T> clazz, SQLQueryParameter parameter);
	/**
	 * 唯一查询
	 * @param parameter
	 * @return
	 */
	Object[] uniqueQuery_(SQLQueryParameter parameter);
	
	/**
	 *  限制查询
	 * @param startRow
	 * @param length
	 * @return
	 */
	List<Map<String, Object>> limitQuery(int startRow, int length, SQLQueryParameter parameter);
	/**
	 *  限制查询
	 * @param clazz
	 * @param startRow
	 * @param length
	 * @param parameter
	 * @return
	 */
	<T> List<T> limitQuery(Class<T> clazz, int startRow, int length, SQLQueryParameter parameter);
	/**
	 * 限制查询
	 * @param startRow
	 * @param length
	 * @param parameter
	 * @return
	 */
	List<Object[]> limitQuery_(int startRow, int length, SQLQueryParameter parameter);
	
	/**
	 * 总数量查询
	 * @param parameter
	 * @return
	 */
	long countQuery(SQLQueryParameter parameter);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param parameter
	 * @return
	 */
	PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, SQLQueryParameter parameter);
	/**
	 * 分页查询
	 * @param clazz
	 * @param pageNum
	 * @param pageSize
	 * @param parameter
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, SQLQueryParameter parameter);
}
