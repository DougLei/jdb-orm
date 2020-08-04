package com.douglei.orm.configuration;

import java.io.InputStream;

import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.sessionfactory.SessionFactory;
import com.douglei.tools.instances.file.resources.reader.AbstractResourcesReader;
import com.douglei.tools.utils.CloseUtil;
import com.douglei.tools.utils.StringUtil;

/**
 * 配置接口
 * <b>Configuration的id、SessionFactory的id、EnvironmentProperty的id, 这几个id都是同一个值, 都是在configuration中配置的id</b>
 * @author DougLei
 */
public abstract class Configuration implements SelfProcessing{
	public static final String DEFAULT_CONF_FILE = "jdb-orm.conf.xml";
	
	protected String configurationFile;
	protected InputStream configurationInputStream;
	
	protected String id;
	protected ExternalDataSource dataSource;
	protected MappingStore mappingStore;
	
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
	protected void setId(String id) {
		if(StringUtil.isEmpty(id)) {
			throw new NullPointerException("["+getClass().getName() + "]的id属性值不能为空");
		}
		this.id = id;
	}
	
	protected InputStream getConfigurationInputStream() {
		if(configurationInputStream == null) {
			if(StringUtil.isEmpty(configurationFile)) {
				throw new NullPointerException("buildSessionFactory时, 必须指定configurationFile或configurationInputStream");
			}
			configurationInputStream = Configuration.class.getClassLoader().getResourceAsStream(configurationFile);
		}
		return configurationInputStream;
	}
	
	protected void closeConfigurationInputStream() {
		if(configurationInputStream != null) {
			CloseUtil.closeIO(configurationInputStream);
			configurationInputStream = null;
		}
	}
	
	/**
	 * 设置配置文件的路径
	 * @param configurationFile
	 */
	public void setConfigurationFile(String configurationFile) {
		this.configurationFile = configurationFile;
	}

	/**
	 * 设置配置信息的InputStream
	 * @param configurationInputStream
	 */
	public void setConfigurationInputStream(InputStream configurationInputStream) {
		closeConfigurationInputStream();
		this.configurationFile = AbstractResourcesReader.DEFAULT_PATH;
		this.configurationInputStream = configurationInputStream;
	}
	
	public void setExternalDataSource(ExternalDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setMappingStore(MappingStore mappingStore) {
		this.mappingStore = mappingStore;
	}
	
	/**
	 * build SessionFactory
	 * 一个configuration也只能有一个sessionFactory实例
	 * @return
	 */
	public final SessionFactory buildSessionFactory() {
		if(sessionFactory == null) {
			JdbConfigurationBean.initial();
			setSessionFactory();
		}
		return sessionFactory;
	}
	
	/**
	 * 设置sessionFactory, 即进行初始化
	 */
	protected abstract void setSessionFactory();
}
