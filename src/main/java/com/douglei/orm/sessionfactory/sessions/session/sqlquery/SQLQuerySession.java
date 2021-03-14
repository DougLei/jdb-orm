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
	 * @param entity
	 * @return
	 */
	List<Map<String, Object>> query(SQLQueryEntity entity);
	/**
	 * 查询
	 * @param clazz
	 * @param entity
	 * @return
	 */
	<T> List<T> query(Class<T> clazz, SQLQueryEntity entity);
	/**
	 * 查询
	 * @param entity
	 * @return
	 */
	List<Object[]> query_(SQLQueryEntity entity);
	
	/**
	 * 唯一查询
	 * @param entity
	 * @return
	 */
	Map<String, Object> uniqueQuery(SQLQueryEntity entity);
	/**
	 * 唯一查询
	 * @param clazz
	 * @param entity
	 * @return
	 */
	<T> T uniqueQuery(Class<T> clazz, SQLQueryEntity entity);
	/**
	 * 唯一查询
	 * @param entity
	 * @return
	 */
	Object[] uniqueQuery_(SQLQueryEntity entity);
	
	/**
	 *  限制查询
	 * @param startRow
	 * @param length
	 * @return
	 */
	List<Map<String, Object>> limitQuery(int startRow, int length, SQLQueryEntity entity);
	/**
	 *  限制查询
	 * @param clazz
	 * @param startRow
	 * @param length
	 * @param entity
	 * @return
	 */
	<T> List<T> limitQuery(Class<T> clazz, int startRow, int length, SQLQueryEntity entity);
	/**
	 * 限制查询
	 * @param startRow
	 * @param length
	 * @param entity
	 * @return
	 */
	List<Object[]> limitQuery_(int startRow, int length, SQLQueryEntity entity);
	
	/**
	 * 总数量查询
	 * @param entity
	 * @return
	 */
	long countQuery(SQLQueryEntity entity);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param entity
	 * @return
	 */
	PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, SQLQueryEntity entity);
	/**
	 * 分页查询
	 * @param clazz
	 * @param pageNum
	 * @param pageSize
	 * @param entity
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, SQLQueryEntity entity);
}
