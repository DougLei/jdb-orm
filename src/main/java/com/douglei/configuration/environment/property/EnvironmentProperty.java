package com.douglei.configuration.environment.property;

import com.douglei.configuration.environment.mapping.cache.store.MappingCacheStore;
import com.douglei.core.dialect.Dialect;
import com.douglei.core.metadata.table.CreateMode;

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
