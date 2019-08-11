package com.douglei.orm.configuration;

import java.io.InputStream;

import javax.sql.DataSource;

import com.douglei.orm.configuration.environment.mapping.cache.store.MappingCacheStore;
import com.douglei.orm.sessionfactory.SessionFactory;
import com.douglei.tools.instances.reader.Reader;
import com.douglei.tools.utils.CloseUtil;
import com.douglei.tools.utils.StringUtil;

/**
 * 配置接口
 * <b>Configuration的id、SessionFactory的id、Environment的id、EnvironmentProperty的id, 这几个id都是同一个值, 都是给configuration中配置的id</b>
 * @author DougLei
 */
public abstract class Configuration implements SelfProcessing{
	public static final String DEFAULT_CONF_FILE = "jdb-orm.conf.xml";
	
	protected String configurationFile;
	protected InputStream configurationInputStream;
	
	protected String id;
	protected ExternalDataSource dataSource;
	protected MappingCacheStore mappingCacheStore;
	
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
		this.configurationFile = Reader.DEFAULT_PATH;
		this.configurationInputStream = configurationInputStream;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = new ExternalDataSource(dataSource);
	}
	public void setDataSource(DataSource dataSource, String closeMethodName) {
		this.dataSource = new ExternalDataSource(dataSource, closeMethodName);
	}
	
	public void setMappingCacheStore(MappingCacheStore mappingCacheStore) {
		this.mappingCacheStore = mappingCacheStore;
	}
	
	/**
	 * build SessionFactory
	 * 一个configuration也只能有一个sessionFactory实例
	 * @return
	 */
	public SessionFactory buildSessionFactory() {
		if(sessionFactory == null) {
			setSessionFactory();
		}
		return sessionFactory;
	}
	
	protected InputStream getConfigurationInputStream() {
		if(this.configurationInputStream == null) {
			if(StringUtil.isEmpty(configurationFile)) {
				throw new NullPointerException("buildSessionFactory时, 必须指定configurationFile或configurationInputStream");
			}
			this.configurationInputStream = Configuration.class.getClassLoader().getResourceAsStream(configurationFile);
		}
		return this.configurationInputStream;
	}
	
	protected void closeConfigurationInputStream() {
		if(this.configurationInputStream != null) {
			CloseUtil.closeIO(this.configurationInputStream);
			this.configurationInputStream = null;
		}
	}
	
	@Override
	public void selfChecking() throws SelfCheckingException {
	}

	protected abstract void setSessionFactory();
}
