package com.douglei.orm.configuration;

import com.douglei.orm.configuration.environment.mapping.cache.store.MappingCacheStore;
import com.douglei.orm.sessionfactory.SessionFactory;

/**
 * 配置接口
 * <b>Configuration的id、SessionFactory的id、Environment的id、EnvironmentProperty的id, 这几个id都是同一个值, 都是给configuration中配置的id</b>
 * @author DougLei
 */
public interface Configuration extends SelfProcessing{
	public static final String DEFAULT_CONF_FILE = "jdb-orm.conf.xml";
	
	/**
	 * 返回该配置对象的id
	 * <p>即配置文件<configuration>节点中的唯一标识</p>
	 * @return
	 */
	String getId();
	
	void setMappingCacheStore(MappingCacheStore mappingCacheStore);
	
	/**
	 * <pre>
	 * 	一个configuration也只能有一个sessionFactory实例
	 * </pre>
	 * @return
	 */
	SessionFactory buildSessionFactory();
}
