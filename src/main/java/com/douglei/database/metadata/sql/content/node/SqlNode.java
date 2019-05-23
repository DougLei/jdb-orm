package com.douglei.database.metadata.sql.content.node;

import java.util.Map;

/**
 * 
 * @author DougLei
 */
public interface SqlNode {
	
	SqlNodeType getType();
	
	/**
	 * 该node是否满足匹配
	 * @param sqlParameterMap
	 * @return
	 */
	boolean matching(Map<String, Object> sqlParameterMap);
	
	/**
	 * 获取可执行的sql node
	 * @param sqlParameterMap
	 * @return
	 */
	ExecuteSqlNode getExecuteSqlNode(Map<String, Object> sqlParameterMap);
}
