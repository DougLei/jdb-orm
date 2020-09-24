package com.douglei.orm.core.dialect.db.object;

/**
 * 数据库对象名称异常
 * @author DougLei
 */
public class DBObjectNameException extends RuntimeException{

	public DBObjectNameException(String message) {
		super(message);
	}
}
