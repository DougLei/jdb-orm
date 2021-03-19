package com.douglei.orm.sessionfactory.sessions.session.sqlquery;

import java.util.List;
import java.util.Map;

import com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl.AbstractParameter;
import com.douglei.orm.sessionfactory.sessions.sqlsession.PageRecursiveEntity;
import com.douglei.orm.sessionfactory.sessions.sqlsession.RecursiveEntity;
import com.douglei.orm.sql.query.page.PageResult;

/**
 * 
 * @author DougLei
 */
public interface SQLQuerySession {
	
	/**
	 * 查询
	 * @param name
	 * @param sqlParameter 
	 * @param parameters
	 * @return
	 */
	List<Map<String, Object>> query(String name, Object sqlParameter, List<AbstractParameter> parameters);
	/**
	 * 查询
	 * @param clazz
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	<T> List<T> query(Class<T> clazz, String name, Object sqlParameter, List<AbstractParameter> parameters);
	/**
	 * 查询
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	List<Object[]> query_(String name, Object sqlParameter, List<AbstractParameter> parameters);
	
	/**
	 * 唯一查询
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	Map<String, Object> uniqueQuery(String name, Object sqlParameter, List<AbstractParameter> parameters);
	/**
	 * 唯一查询
	 * @param clazz
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	<T> T uniqueQuery(Class<T> clazz, String name, Object sqlParameter, List<AbstractParameter> parameters);
	/**
	 * 唯一查询
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	Object[] uniqueQuery_(String name, Object sqlParameter, List<AbstractParameter> parameters);
	
	/**
	 * 限制查询
	 * @param startRow
	 * @param length
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	List<Map<String, Object>> limitQuery(int startRow, int length, String name, Object sqlParameter, List<AbstractParameter> parameters);
	/**
	 * 限制查询
	 * @param clazz
	 * @param startRow
	 * @param length
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	<T> List<T> limitQuery(Class<T> clazz, int startRow, int length, String name, Object sqlParameter, List<AbstractParameter> parameters);
	/**
	 * 限制查询
	 * @param startRow
	 * @param length
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	List<Object[]> limitQuery_(int startRow, int length, String name, Object sqlParameter, List<AbstractParameter> parameters);
	
	/**
	 * 总数量查询
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	long countQuery(String name, Object sqlParameter, List<AbstractParameter> parameters);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String name, Object sqlParameter, List<AbstractParameter> parameters);
	/**
	 * 分页查询
	 * @param clazz
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, String name, Object sqlParameter, List<AbstractParameter> parameters);
	
	/**
	 * 递归查询
	 * @param entity1
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	List<Map<String, Object>> recursiveQuery(RecursiveEntity entity1, String name, Object sqlParameter, List<AbstractParameter> parameters);
	/**
	 * 递归查询
	 * @param clazz
	 * @param entity
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	<T> List<T> recursiveQuery(Class<T> clazz, RecursiveEntity entity, String name, Object sqlParameter, List<AbstractParameter> parameters);
	
	/**
	 * 分页递归查询
	 * @param entity
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	PageResult<Map<String, Object>> pageRecursiveQuery(PageRecursiveEntity entity, String name, Object sqlParameter, List<AbstractParameter> parameters);
	/**
	 * 分页递归查询
	 * @param clazz
	 * @param entity
	 * @param name
	 * @param sqlParameter
	 * @param parameters
	 * @return
	 */
	<T> PageResult<T> pageRecursiveQuery(Class<T> clazz, PageRecursiveEntity entity, String name, Object sqlParameter, List<AbstractParameter> parameters);
}
