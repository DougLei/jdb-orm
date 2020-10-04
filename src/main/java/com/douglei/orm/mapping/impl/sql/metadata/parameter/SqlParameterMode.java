package com.douglei.orm.mapping.impl.sql.metadata.parameter;

/**
 * 
 * @author DougLei
 */
public enum SqlParameterMode {
	IN,
	OUT,
	INOUT;

	public static SqlParameterMode toValue(String mode) {
		mode = mode.toUpperCase();
		SqlParameterMode[] spms = SqlParameterMode.values();
		for (SqlParameterMode sqlParameterMode : spms) {
			if(sqlParameterMode.name().equals(mode)) {
				return sqlParameterMode;
			}
		}
		return null;
	}
}
