package com.douglei.core.dialect.db.sql.entity;

import java.util.ArrayList;
import java.util.List;

import com.douglei.core.dialect.DialectType;
import com.douglei.core.metadata.sql.SqlContentType;

/**
 * 
 * @author DougLei
 */
public class SqlContent {
	private DialectType dialectType;
	private SqlContentType type;
	private String content;
	private List<SqlParameter> parametersByDefinedOrder; 
	
	public SqlContent(SqlContentType contentType, String content) {
		this(null, contentType, content);
	}
	public SqlContent(DialectType dialectType, SqlContentType type, String content) {
		this.dialectType = dialectType;
		this.type = type;
		this.content = content;
	}
	
	private void initialParameters() {
		if(parametersByDefinedOrder == null) {
			parametersByDefinedOrder = new ArrayList<SqlParameter>(10);
		}
	}
	public void addParameter(String parameterConfigurationText) {
		initialParameters();
		parametersByDefinedOrder.add(new SqlParameter(parameterConfigurationText));
	}
	
	public SqlContentType getType() {
		return type;
	}
	public DialectType getDialectType() {
		return dialectType;
	}
	public String getContent() {
		return content;
	}
	public List<SqlParameter> getParameters() {
		return parametersByDefinedOrder;
	}
}
