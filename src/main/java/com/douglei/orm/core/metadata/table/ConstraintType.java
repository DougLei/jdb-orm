package com.douglei.orm.core.metadata.table;

import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum ConstraintType {

	/**
	 * 主键约束
	 */
	PRIMARY_KEY("PK", "PRIMARY KEY", 4),
	/**
	 * 唯一值约束
	 */
	UNIQUE("UQ", "UNIQUE", 4),
	/**
	 * 默认值约束
	 */
	DEFAULT_VALUE("DF", "DEFAULT", 1),
	/**
	 * 检查约束
	 */
	CHECK("CK", "CHECK", 1),
	/**
	 * 外键约束
	 */
	FOREIGN_KEY("FK", "FOREIGN KEY", 1);
	
	private String constraintPrefix;
	private String sqlStatement;
	private byte supportColumnCount;// 支持的列数量, 如果大于1, 则是复合约束
	private ConstraintType(String constraintPrefix, String sqlStatement, int supportColumnCount) {
		this.constraintPrefix = constraintPrefix;
		this.sqlStatement = sqlStatement;
		this.supportColumnCount = (byte)supportColumnCount;
	}
	
	public String getConstraintPrefix() {
		return constraintPrefix;
	}
	public String getSqlStatement() {
		return sqlStatement;
	}
	public byte supportColumnCount() {
		return supportColumnCount;
	}

	public static ConstraintType toValue(String type) {
		if(StringUtil.notEmpty(type)) {
			type = type.toUpperCase();
			ConstraintType[] cts = ConstraintType.values();
			for (ConstraintType ct : cts) {
				if(ct.name().equals(type)) {
					return ct;
				}
			}
		}
		return null;
	}
}
