package com.douglei.database.dialect.impl.mssql;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.TransactionIsolationLevel;

/**
 * 
 * @author DougLei
 */
public class MSSqlDialect implements Dialect{

	public String getCode() {
		return "MSSQL";
	}
	
	public TransactionIsolationLevel getDefaultTransactionIsolationLevel() {
		return TransactionIsolationLevel.READ_COMMITTED;
	}
}
