package com.douglei.database.metadata.sql.content;

/**
 * 
 * @author DougLei
 */
public enum SqlContentType {
	INSERT,
	DELETE,
	UPDATE,
	SELECT;
	
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
