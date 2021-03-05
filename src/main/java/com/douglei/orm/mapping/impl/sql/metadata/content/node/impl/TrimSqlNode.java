package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNode extends AbstractNestingNode {
	private String prefix;
	private String suffix;
	
	private String[] prefixOverride;
	private String[] suffixOverride;
	
	public TrimSqlNode(String prefix, String suffix, String[] prefixOverride, String[] suffixOverride) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.prefixOverride = prefixOverride;
		this.suffixOverride = suffixOverride;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TRIM;
	}
}