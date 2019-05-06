package com.douglei.database.sql.statement;

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
	 */
	List<Map<String, Object>> getQueryResultList(List<? extends Object> parameters);
	
	/**
	 * 执行查询, 获取唯一结果
	 * @param parameters
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> getQueryUniqueResult(List<? extends Object> parameters);
	
	/**
	 * 执行查询, 获取结果集
	 * @param parameters
	 * @return 返回<值>的list-数组集合
	 */
	List<Object[]> getQueryResultList_(List<? extends Object> parameters);
	
	/**
	 * 执行查询, 获取唯一结果
	 * @param parameters
	 * @return 返回<值>的数组
	 */
	Object[] getQueryUniqueResult_(List<? extends Object> parameters);
	
	/**
	 * 执行增删改操作, 返回操作数据的数量
	 * @param parameters
	 * @return
	 */
	int executeUpdate(List<? extends Object> parameters);
	
	/**
	 * 是否关闭
	 * @return
	 */
	boolean isClosed();
	
	/**
	 * 标识该StatementHandler是否已经执行过一次
	 * @return
	 */
	public boolean isExecuted();
	
	/**
	 * 关闭
	 */
	void close();
}
