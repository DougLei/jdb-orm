package com.douglei.database.metadata.sql.content.node;

/**
 * 
 * @author DougLei
 */
public interface SqlNode {
	
	SqlNodeType getType();
	
	/**
	 * 该node是否满足匹配
	 * @param sqlParameter
	 * @return
	 */
	boolean matching(Object sqlParameter);
	
	/**
	 * 该node是否满足匹配
	 * @param sqlParameter
	 * @param alias
	 * @return
	 */
	boolean matching(Object sqlParameter, String alias);
	
	/**
	 * 获取可执行的sql node
	 * @param sqlParameter
	 * @return
	 */
	ExecuteSqlNode getExecuteSqlNode(Object sqlParameter);
	/**
	 * 获取可执行的sql node
	 * @param sqlParameter
	 * @param sqlParameterNamePrefix sql参数名前缀 
	 * @return
	 */
	ExecuteSqlNode getExecuteSqlNode(Object sqlParameter, String sqlParameterNamePrefix);
}
