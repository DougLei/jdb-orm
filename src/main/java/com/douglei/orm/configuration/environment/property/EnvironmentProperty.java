package com.douglei.orm.configuration.environment.property;

import com.douglei.orm.configuration.environment.mapping.cache.store.MappingCacheStore;
import com.douglei.orm.core.dialect.Dialect;
import com.douglei.orm.core.metadata.table.CreateMode;

/**
 * 
 * @author DougLei
 */
public interface EnvironmentProperty {
	
	public String getId();
	
	public Dialect getDialect();
	
	public boolean getEnableSessionCache();
	
	public boolean getEnableTableSessionCache();
	
	public MappingCacheStore getMappingCacheStore();
	
	public CreateMode getTableCreateMode();
	
	public String getSerializationFileRootPath();
	
	public boolean getEnableDataValidation();
}
