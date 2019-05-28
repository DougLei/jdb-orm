package com.douglei.database.metadata.table.column.extend;

/**
 * 
 * @author DougLei
 */
public enum ConstraintType {
	// TODO 目前没有提供外键约束、检查约束
	
	/**
	 * 主键约束
	 */
	PRIMARY_KEY("PK"),
	/**
	 * 唯一值约束
	 */
	UNIQUE("UQ"),
	/**
	 * 默认值约束
	 */
	DEFAULT_VALUE("DF");
	
	private String constraintPrefix;
	private ConstraintType(String constraintPrefix) {
		this.constraintPrefix = constraintPrefix;
	}
	
	public String getConstraintPrefix() {
		return constraintPrefix;
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
