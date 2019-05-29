package com.douglei.configuration.environment.property;

import com.douglei.configuration.environment.property.mapping.store.target.MappingCacheStore;
import com.douglei.database.dialect.Dialect;
import com.douglei.database.metadata.table.CreateMode;

/**
 * 
 * @author DougLei
 */
public interface EnvironmentProperty {
	
	public Dialect getDialect();
	
	public boolean getEnableSessionCache();
	
	public MappingCacheStore getMappingCacheStore();
	
	public CreateMode getTableCreateMode();
}
