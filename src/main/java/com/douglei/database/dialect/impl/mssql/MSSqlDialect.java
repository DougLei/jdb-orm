package com.douglei.database.dialect.impl.mssql;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.TransactionIsolationLevel;

/**
 * 
 * @author DougLei
 */
public final class MSSqlDialect implements Dialect{
	private MSSqlDialect() {}
	private static final MSSqlDialect instance =new MSSqlDialect();
	public static final MSSqlDialect singleInstance() {
		return instance;
	}
	
	public String getDatabaseCode() {
		return "MSSQL";
	}
	
	public TransactionIsolationLevel getDefaultTransactionIsolationLevel() {
		return TransactionIsolationLevel.READ_COMMITTED;
	}
}
