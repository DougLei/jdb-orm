package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class ForeachSqlNode extends AbstractNestingNode {
	private String collection;
	private String alias;
	
	private String open;
	private String separator;
	private String close;
	
	public ForeachSqlNode(String collection, String alias, String open, String separator, String close) {
		this.collection = collection;
		this.alias = alias;
		this.open = open;
		this.separator = separator;
		this.close = close;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.FOREACH;
	}

	public String getCollection() {
		return collection;
	}
	public String getAlias() {
		return alias;
	}
	public String getOpen() {
		return open;
	}
	public String getSeparator() {
		return separator;
	}
	public String getClose() {
		return close;
	}
}