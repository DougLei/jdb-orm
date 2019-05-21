package com.douglei.database.metadata.sql.content.node;

import java.util.List;
import java.util.Map;

import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.sql.SqlParameterMetadata;

/**
 * 
 * @author DougLei
 */
public interface NodeMetadata extends Metadata{
	
	String getContent();
	List<SqlParameterMetadata> getSqlParameterByDefinedOrders();
	
	/**
	 * 该node是否满足匹配
	 * @param sqlParameterMap
	 * @return
	 */
	boolean isMatching(Map<String, Object> sqlParameterMap);

}
