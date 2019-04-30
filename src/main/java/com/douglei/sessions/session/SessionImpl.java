package com.douglei.sessions.session;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.Mapping;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.sessions.AbstractSession;
import com.douglei.sessions.Session;
import com.douglei.sessions.session.persistent.Persistent;
import com.douglei.sessions.session.persistent.PersistentFactory;
import com.douglei.sessions.session.persistent.PersistentObject;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class SessionImpl extends AbstractSession implements Session {
	private static final Logger logger = LoggerFactory.getLogger(SessionImpl.class);
	private Map<String, PersistentObject> insertPersistentObjectCache;
	private Map<String, PersistentObject> updatePersistentObjectCache;
	private Map<String, PersistentObject> deletePersistentObjectCache;
	
	public SessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
		if(enableSessionCache) {
			initialSessionCaches();
		}
	}
	
	// 初始化session caches
	private void initialSessionCaches() {
		insertPersistentObjectCache = new HashMap<String, PersistentObject>();
		updatePersistentObjectCache = new HashMap<String, PersistentObject>();
		deletePersistentObjectCache = new HashMap<String, PersistentObject>();
	}

	@Override
	public void save(Object object) {
		if(object == null) {
			logger.debug("要save的对象不能为空");
			throw new NullPointerException("要save的对象不能为空");
		}
		String code = object.getClass().getName();
		logger.debug("对实体对象{} 进行save操作", code);
		
		Mapping mapping = mappingWrapper.getMapping(code);
		if(mapping == null) {
			logger.debug("不存在code为[{}]的映射", code);
			throw new NullPointerException("不存在code为["+code+"]的映射");
		}
		
		Metadata metadata = mapping.getMetadata();
		Persistent persistent = PersistentFactory.buildPersistent(metadata, object);
		
		
		
	}
	
	@Override
	public void save(String code, Map<String, Object> propertyMap) {
		if(StringUtil.isEmpty(code)) {
			logger.debug("要save的对象的code值不能为空");
			throw new NullPointerException("要save的对象的code值不能为空");
		}
		logger.debug("对code={} 的对象进行save操作", code);
		
		Mapping mapping = mappingWrapper.getMapping(code);
		if(mapping == null) {
			logger.debug("不存在code为[{}]的映射", code);
			throw new NullPointerException("不存在code为["+code+"]的映射");
		}
		
		Metadata metadata = mapping.getMetadata();
		Persistent persistent = PersistentFactory.buildPersistent(metadata, propertyMap);
		
		
		
		
	}
	
	
	@Override
	protected void flush() {
		if(enableSessionCache) {
			
		}
	}

	@Override
	public void close() {
		flush();
		super.close();
	}
}
