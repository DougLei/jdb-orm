package com.douglei.orm.configuration.environment;

import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.core.mapping.MappingHandler;

/**
 * 
 * @author DougLei
 */
public interface Environment extends SelfProcessing{

	/**
	 * 获取数据源包装类
	 * @return
	 */
	DataSourceWrapper getDataSourceWrapper();
	
	/**
	 * 获取环境属性实例
	 * @return
	 */
	EnvironmentProperty getEnvironmentProperty();
	
	/**
	 * 获取映射处理器
	 * @return
	 */
	MappingHandler getMappingHandler();
}
