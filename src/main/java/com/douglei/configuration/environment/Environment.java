package com.douglei.configuration.environment;

import com.douglei.configuration.SelfProcessing;
import com.douglei.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;

/**
 * 
 * @author DougLei
 */
public interface Environment extends SelfProcessing{
	
	public EnvironmentProperty getEnvironmentProperty();
	
	public DataSourceWrapper getDataSourceWrapper();
	
	public MappingWrapper getMappingWrapper();
}
