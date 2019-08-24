package com.douglei.orm.core.metadata.sql;

/**
 * 
 * @author DougLei
 */
public enum SqlContentType {
	_SQL_CONTENT_, // 对应sql-content元素特有的类型, 不对外配置
	
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
