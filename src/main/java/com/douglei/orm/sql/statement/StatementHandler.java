package com.douglei.orm.sql.statement;

import java.util.List;
import java.util.Map;

/**
 * java.sql.Statement的处理器接口
 * @author DougLei
 */
public interface StatementHandler {

	/**
	 * 执行查询, 获取结果集
	 * @param parameters
	 * @return 返回<列名:值>的list-map集合
	 * @throws StatementExecutionException
	 */
	List<Map<String, Object>> executeQueryResultList(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 执行限制查询, 获取结果集
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param parameters
	 * @return 返回<列名:值>的list-map集合
	 * @throws StatementExecutionException
	 */
	List<Map<String, Object>> executeLimitQueryResultList(int startRow, int length, List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 执行查询, 获取唯一结果
	 * @param parameters
	 * @return 返回<列名:值>的map集合
	 * @throws StatementExecutionException
	 */
	Map<String, Object> executeQueryUniqueResult(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 执行查询, 获取结果集
	 * @param parameters
	 * @return 返回<值>的list-数组集合
	 * @throws StatementExecutionException
	 */
	List<Object[]> executeQueryResultList_(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 执行限制查询, 获取结果集
	 * @param startRow 起始的行数, 值从1开始, 小于1时会修正为1
	 * @param length 长度, 小于1时会修正为1
	 * @param parameters
	 * @return 返回<值>的list-数组集合
	 * @throws StatementExecutionException
	 */
	List<Object[]> executeLimitQueryResultList_(int startRow, int length, List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 执行查询, 获取唯一结果
	 * @param parameters
	 * @return 返回<值>的数组
	 * @throws StatementExecutionException
	 */
	Object[] executeQueryUniqueResult_(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 执行新增操作
	 * @param parameters
	 * @return 
	 * @throws StatementExecutionException
	 */
	InsertResult executeInsert(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 执行增删改操作
	 * @param parameters
	 * @return 影响的行数
	 * @throws StatementExecutionException
	 */
	int executeUpdate(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 当前StatementHandler是否支持缓存
	 * @return
	 */
	default boolean supportCache() {
		return true;
	}
	
	/**
	 * 关闭
	 */
	void close();
	
	/**
	 * 是否关闭
	 * @return
	 */
	boolean isClosed();
}
