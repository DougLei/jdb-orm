package com.douglei.orm.configuration;

import java.io.InputStream;

import com.douglei.orm.configuration.environment.mapping.container.MappingContainer;
import com.douglei.orm.sessionfactory.SessionFactory;
import com.douglei.tools.utils.StringUtil;

/**
 * 配置接口
 * Configuration的id、SessionFactory的id、EnvironmentProperty的id, 这几个id都是同一个值, 都是在configuration中配置的id
 * @author DougLei
 */
public abstract class Configuration implements SelfProcessing{
	public static final String DEFAULT_CONFIGURATION_FILE_PATH = "jdb-orm.conf.xml"; // 默认的配置文件路径
	
	protected InputStream configurationInputStream; // 配置文件的流对象
	protected String id;
	protected ExternalDataSource exDataSource;
	protected MappingContainer mappingContainer;
	
	protected SessionFactory sessionFactory;
	
	/**
	 * Configuration的唯一标识
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 设置id
	 * @param id
	 */
	public void setId(String id) {
		if(this.id == null) {
			if(StringUtil.isEmpty(id)) 
				throw new NullPointerException("["+getClass().getName() + "]的id属性值不能为空");
			this.id = id;
		}
	}
	
	/**
	 * 设置配置文件的流
	 * @param configurationInputStream
	 */
	protected void setConfigurationInputStream(InputStream configurationInputStream) {
		this.configurationInputStream = configurationInputStream;
	}

	/**
	 * 设置外部的数据源
	 * @param exDataSource
	 */
	public void setExternalDataSource(ExternalDataSource exDataSource) {
		this.exDataSource = exDataSource;
	}
	
	/**
	 * 设置映射的存储容器
	 * @param mappingContainer
	 */
	public void setMappingContainer(MappingContainer mappingContainer) {
		this.mappingContainer = mappingContainer;
	}
	
	/**
	 * 构建 SessionFactory 实例
	 * 一个{@link Configuration} 也只能生成一个{@link SessionFactory}实例
	 * @return
	 */
	public final SessionFactory buildSessionFactory() {
		if(sessionFactory == null)
			initialSessionFactory();
		return sessionFactory;
	}
	
	/**
	 * 初始化sessionFactory
	 */
	protected abstract void initialSessionFactory();
}
