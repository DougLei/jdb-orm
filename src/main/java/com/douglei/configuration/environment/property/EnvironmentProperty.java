package com.douglei.configuration.environment.property;

import com.douglei.configuration.environment.property.mapping.store.target.MappingStore;
import com.douglei.database.dialect.Dialect;
import com.douglei.database.metadata.table.CreateMode;

/**
 * 
 * @author DougLei
 */
public interface EnvironmentProperty {
	
	public Dialect getDialect();
	
	public boolean getEnableSessionCache();
	
	public MappingStore getMappingStore();
	
	public CreateMode getTableCreateMode();
}
