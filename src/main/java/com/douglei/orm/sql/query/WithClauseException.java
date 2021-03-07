package com.douglei.orm.sql.query;

import com.douglei.orm.configuration.OrmException;

/**
 * with子句异常
 * @author DougLei
 */
public class WithClauseException extends OrmException{

	public WithClauseException(String message) {
		super(message);
	}
}