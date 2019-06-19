package com.douglei.orm.core.dialect.db.table.entity;

/**
 * 
 * @author DougLei
 */
public enum ConstraintType {

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
	DEFAULT_VALUE("DF", "DEFAULT"),
	/**
	 * 检查约束
	 */
	CHECK("CK", "CHECK"),
	/**
	 * 外键约束
	 */
	FOREIGN_KEY("FK", "FOREIGN KEY");
	
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
