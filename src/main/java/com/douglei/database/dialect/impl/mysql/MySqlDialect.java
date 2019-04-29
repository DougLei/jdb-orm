package com.douglei.database.dialect.impl.mysql;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.TransactionIsolationLevel;

/**
 * 
 * @author DougLei
 */
public final class MySqlDialect implements Dialect{
	private MySqlDialect() {}
	private static final MySqlDialect instance =new MySqlDialect();
	public static final MySqlDialect singleInstance() {
		return instance;
	}

	public String getDatabaseCode() {
		return "MYSQL";
	}
	
	public TransactionIsolationLevel getDefaultTransactionIsolationLevel() {
		return TransactionIsolationLevel.READ_COMMITTED;
	}
}
