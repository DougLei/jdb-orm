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
	List<Map<String, Object>> executeQueryResultList(List<Object> parameters) throws StatementExecutionException;
	
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
	 * 执行查询, 获取唯一结果
	 * @param parameters
	 * @return 返回<值>的数组
	 * @throws StatementExecutionException
	 */
	Object[] executeQueryUniqueResult_(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 执行增删改操作, 返回操作数据的数量
	 * @param parameters
	 * @return
	 * @throws StatementExecutionException
	 */
	int executeUpdate(List<Object> parameters) throws StatementExecutionException;
	
	/**
	 * 当前StatementHandler是否可以缓存
	 * @return
	 */
	default boolean canCache() {
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
