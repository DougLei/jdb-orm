package com.douglei.database.metadata.sql.content;

/**
 * 
 * @author DougLei
 */
public enum Type {
	INSERT,
	DELETE,
	UPDATE,
	SELECT;
	
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
