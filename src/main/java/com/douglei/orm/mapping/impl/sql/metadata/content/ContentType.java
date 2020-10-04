package com.douglei.orm.mapping.impl.sql.metadata.content;

/**
 * 
 * @author DougLei
 */
public enum ContentType {
	INSERT,
	DELETE,
	UPDATE,
	SELECT,
	
	DECLARE,
	PROCEDURE;
	
	public static ContentType toValue(String type) {
		type = type.toUpperCase();
		ContentType[] scts = ContentType.values();
		for (ContentType sct : scts) {
			if(sct.name().equals(type)) {
				return sct;
			}
		}
		return null;
	}
}
