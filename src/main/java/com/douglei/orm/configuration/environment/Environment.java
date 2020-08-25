package com.douglei.orm.configuration.environment;

import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.MappingStoreWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;

/**
 * 
 * @author DougLei
 */
public interface Environment extends SelfProcessing{
	
	public EnvironmentProperty getEnvironmentProperty();
	
	public DataSourceWrapper getDataSourceWrapper();
	
	public MappingStoreWrapper getMappingWrapper();
}
