package com.douglei.core.dialect.db.objectname;

/**
 * 数据库对象名称异常
 * @author DougLei
 */
public class DBObjectNameException extends RuntimeException{
	private static final long serialVersionUID = -8896827282989269342L;

	public DBObjectNameException(String message) {
		super(message);
	}
}
