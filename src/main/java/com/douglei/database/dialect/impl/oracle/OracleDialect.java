package com.douglei.database.dialect.impl.oracle;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.TransactionIsolationLevel;

/**
 * 
 * @author DougLei
 */
public final class OracleDialect implements Dialect{
	private OracleDialect() {}
	private static final OracleDialect instance =new OracleDialect();
	public static final OracleDialect singleInstance() {
		return instance;
	}
	
	public String getDatabaseCode() {
		return "ORACLE";
	}
	
	public TransactionIsolationLevel getDefaultTransactionIsolationLevel() {
		return TransactionIsolationLevel.READ_COMMITTED;
	}
}
