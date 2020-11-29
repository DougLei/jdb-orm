package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node;

/**
 * 
 * @author DougLei
 */
public enum SqlNodeType {
	TEXT("#text"), IF, ELSE, TRIM, SWITCH, FOREACH, WHERE, SET, INCLUDE;
	
	private String nodeName; // xml的节点名
	private SqlNodeType() {
		this.nodeName = name().toLowerCase();
	}
	private SqlNodeType(String name) {
		this.nodeName = name;
	}
	public String getNodeName() {
		return nodeName;
	}
}
