package com.douglei.database.metadata.sql;

/**
 * 
 * @author DougLei
 */
public enum SqlContentType {
	INSERT,
	DELETE,
	UPDATE,
	SELECT,
	
	PROCEDURE;
	
	public static SqlContentType toValue(String type) {
		type = type.toUpperCase();
		SqlContentType[] scts = SqlContentType.values();
		for (SqlContentType sct : scts) {
			if(sct.name().equals(type)) {
				return sct;
			}
		}
		return null;
	}
}
