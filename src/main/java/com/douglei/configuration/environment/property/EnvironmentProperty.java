package com.douglei.configuration.environment.property;

import com.douglei.configuration.environment.property.mapping.store.target.MappingStore;
import com.douglei.database.dialect.Dialect;

/**
 * 
 * @author DougLei
 */
public interface EnvironmentProperty {
	
	public Dialect getDialect();
	
	public boolean getEnableSessionCache();
	
	public MappingStore getMappingStore();
}
