package com.douglei.database.dialect.impl.oracle;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.TransactionIsolationLevel;

/**
 * 
 * @author DougLei
 */
public class OracleDialect implements Dialect{
	
	public String getCode() {
		return "ORACLE";
	}
	
	public TransactionIsolationLevel getDefaultTransactionIsolationLevel() {
		return TransactionIsolationLevel.READ_COMMITTED;
	}
}
