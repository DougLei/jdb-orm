package com.douglei.orm.core.dialect.db.table.entity;

import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum IndexType {

	UNKUNO("UN", "UNKONW", true);// TODO 有待后续完善索引配置
	
	private String constraintPrefix;
	private String sqlStatement;
	private boolean supportMultipleColumn;// 是否支持多列, 即复合索引
	private IndexType(String constraintPrefix, String sqlStatement, boolean supportMultipleColumn) {
		this.constraintPrefix = constraintPrefix;
		this.sqlStatement = sqlStatement;
		this.supportMultipleColumn = supportMultipleColumn;
	}
	
	public String getConstraintPrefix() {
		return constraintPrefix;
	}
	public String getSqlStatement() {
		return sqlStatement;
	}
	public boolean supportMultipleColumn() {
		return supportMultipleColumn;
	}

	public static IndexType toValue(String type) {
		if(StringUtil.notEmpty(type)) {
			type = type.toUpperCase();
			IndexType[] its = IndexType.values();
			for (IndexType it : its) {
				if(it.name().equals(type)) {
					return it;
				}
			}
		}
		return null;
	}
}
