package com.douglei.database.dialect.impl.mysql;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.TransactionIsolationLevel;

/**
 * 
 * @author DougLei
 */
public class MySqlDialect implements Dialect{

	public String getCode() {
		return "MYSQL";
	}
	
	public TransactionIsolationLevel getDefaultTransactionIsolationLevel() {
		return TransactionIsolationLevel.READ_COMMITTED;
	}
}
