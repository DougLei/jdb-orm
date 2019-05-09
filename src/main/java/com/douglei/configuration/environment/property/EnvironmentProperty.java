package com.douglei.configuration.environment.property;

import com.douglei.database.dialect.Dialect;

/**
 * 
 * @author DougLei
 */
public interface EnvironmentProperty {
	
	public Dialect getDialect();
	
	public boolean getEnableSessionCache();
}
