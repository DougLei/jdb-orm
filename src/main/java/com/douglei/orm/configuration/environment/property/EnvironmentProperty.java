package com.douglei.orm.configuration.environment.property;

import com.douglei.orm.configuration.environment.mapping.cache.store.MappingStore;
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
	
	public MappingStore getMappingStore();
	
	public CreateMode getTableCreateMode();
	
	public boolean getEnableDataValidation();
	
	public boolean getEnableTableDynamicUpdate();
	
	public String getSerializationFileRootPath();
	
	public boolean getEnableColumnDynamicUpdateValidation();
}
