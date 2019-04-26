package com.douglei.database.sql.statement;

import java.util.List;
import java.util.Map;

/**
 * java.sql.Statement的处理器接口
 * @author DougLei
 */
public interface StatementHandler {

	void close();
	boolean isClosed();
	
	/**
	 * 标识该StatementHandler是否已经执行过一次
	 * @return
	 */
	public boolean isExecuted();

	/**
	 * 执行查询, 获取结果集
	 * @param parameters
	 * @return
	 */
	List<Map<String, Object>> getQueryResultList(List<Object> parameters);
}
