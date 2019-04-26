package com.douglei.configuration;

import com.douglei.configuration.environment.Environment;
import com.douglei.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.sessionfactory.SessionFactory;

/**
 * 配置接口
 * @author DougLei
 */
public interface Configuration extends SelfProcessing{
	String DEFAULT_CONF_FILE_PATH = "jdb-access.conf.xml";
	
	/**
	 * 返回该配置对象的id
	 * <p>即配置文件<configuration>节点中的唯一标识</p>
	 * @return
	 */
	String getId();
	
	/**
	 * <pre>
	 * 	一个configuration也只能有一个sessionFactory实例
	 * </pre>
	 * @return
	 */
	SessionFactory buildSessionFactory();
	
	/**
	 * <pre>
	 * 	获取sessionFactory实例
	 * 	如果不存在，则build后再获取
	 * </pre>
	 * @return
	 */
	SessionFactory getSessionFactory();
	
	/**
	 * 获取environment实例
	 * @return
	 */
	Environment getEnvironment();
	
	DataSourceWrapper getDataSourceWrapper();
	
	EnvironmentProperty getEnvironmentProperty();
	
	MappingWrapper getMappingWrapper();
}
