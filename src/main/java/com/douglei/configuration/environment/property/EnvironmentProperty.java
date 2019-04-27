package com.douglei.configuration.environment.property;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.TransactionIsolationLevel;

/**
 * 
 * @author DougLei
 */
public interface EnvironmentProperty {
	
	public Dialect getDialect();
	
	public TransactionIsolationLevel getTransactionIsolationLevel();
	
	public boolean getEnableSessionCache();
}
