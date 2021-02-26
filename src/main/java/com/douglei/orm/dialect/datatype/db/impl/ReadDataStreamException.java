package com.douglei.orm.dialect.datatype.db.impl;

import com.douglei.orm.configuration.OrmException;

/**
 * 读取数据流异常
 * @author DougLei
 */
public class ReadDataStreamException extends OrmException{

	public ReadDataStreamException(String message, Exception t) {
		super(message, t);
	}
}
