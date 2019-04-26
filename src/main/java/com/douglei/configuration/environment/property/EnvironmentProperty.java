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
	
	/**
	 * 获取是否启用session缓存
	 * @return
	 */
	public boolean getEnableSessionCache();
}
