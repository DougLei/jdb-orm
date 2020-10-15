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
		for (ContentType contentType : ContentType.values()) {
			if(contentType.name().equals(type)) 
				return contentType;
		}
		return null;
	}
}
