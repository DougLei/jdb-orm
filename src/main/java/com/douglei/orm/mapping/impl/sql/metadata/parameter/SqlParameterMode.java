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
		if(OUT.name().equalsIgnoreCase(mode))
			return OUT;
		if(INOUT.name().equalsIgnoreCase(mode))
			return INOUT;
		return IN;
	}
}
