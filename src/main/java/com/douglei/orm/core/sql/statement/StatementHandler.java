package com.douglei.orm.core.sql.statement;

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
	List<Map<String, Object>> getQueryResultList(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 执行查询, 获取唯一结果
	 * @param parameters
	 * @return 返回<列名:值>的map集合
	 * @throws StatementExecutionException
	 */
	Map<String, Object> getQueryUniqueResult(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 执行查询, 获取结果集
	 * @param parameters
	 * @return 返回<值>的list-数组集合
	 * @throws StatementExecutionException
	 */
	List<Object[]> getQueryResultList_(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 执行查询, 获取唯一结果
	 * @param parameters
	 * @return 返回<值>的数组
	 * @throws StatementExecutionException
	 */
	Object[] getQueryUniqueResult_(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 执行增删改操作, 返回操作数据的数量
	 * @param parameters
	 * @return
	 * @throws StatementExecutionException
	 */
	int executeUpdate(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 关闭
	 */
	void close();
	
	/**
	 * 标识该StatementHandler是否已经执行过一次
	 * @return
	 */
	public boolean isExecuted();
	
	/**
	 * 是否关闭
	 * @return
	 */
	boolean isClosed();
}
