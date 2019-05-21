package com.douglei.database.metadata.sql;

/**
 * 
 * @author DougLei
 */
public enum Type {
	INSERT,
	DELETE,
	UPDATE,
	SELECT,
	
	PROCEDURE;
	
	public static Type toValue(String type) {
		type = type.toUpperCase();
		Type[] scts = Type.values();
		for (Type sct : scts) {
			if(sct.name().equals(type)) {
				return sct;
			}
		}
		return null;
	}
}
