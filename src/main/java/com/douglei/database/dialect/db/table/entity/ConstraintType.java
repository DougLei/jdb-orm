package com.douglei.database.dialect.db.table.entity;

/**
 * 
 * @author DougLei
 */
public enum ConstraintType {
	// TODO 目前没有提供外键约束、检查约束
	
	/**
	 * 主键约束
	 */
	PRIMARY_KEY("PK", "PRIMARY KEY"),
	/**
	 * 唯一值约束
	 */
	UNIQUE("UQ", "UNIQUE"),
	/**
	 * 默认值约束
	 */
	DEFAULT_VALUE("DF", "DEFAULT");
	
	private String constraintPrefix;
	private String sqlStatement;
	private ConstraintType(String constraintPrefix, String sqlStatement) {
		this.constraintPrefix = constraintPrefix;
		this.sqlStatement = sqlStatement;
	}
	
	public String getConstraintPrefix() {
		return constraintPrefix;
	}
	public String getSqlStatement() {
		return sqlStatement;
	}

	public static ConstraintType toValue(String type) {
		type = type.toUpperCase();
		ConstraintType[] cts = ConstraintType.values();
		for (ConstraintType ct : cts) {
			if(ct.name().equals(type)) {
				return ct;
			}
		}
		return null;
	}
}
