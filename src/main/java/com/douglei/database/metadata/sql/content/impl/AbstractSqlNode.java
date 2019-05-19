package com.douglei.database.metadata.sql.content.impl;

import java.util.List;

import com.douglei.database.metadata.sql.SqlParameterMetadata;
import com.douglei.database.metadata.sql.content.SqlNode;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractSqlNode implements SqlNode{
	
	private int placeholderCount;
	
	// sql参数, 按照配置中定义的顺序记录
	private List<SqlParameterMetadata> sqlParameterOrders;
}
