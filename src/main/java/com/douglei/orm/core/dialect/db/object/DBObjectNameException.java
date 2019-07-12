package com.douglei.orm.core.dialect.db.object;

/**
 * 数据库对象名称异常
 * @author DougLei
 */
public class DBObjectNameException extends RuntimeException{
	private static final long serialVersionUID = 4755976187768742182L;

	public DBObjectNameException(String message) {
		super(message);
	}
}
