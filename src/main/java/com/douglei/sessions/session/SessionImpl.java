package com.douglei.sessions.session;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.Mapping;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.sessions.AbstractSession;
import com.douglei.sessions.Session;
import com.douglei.sessions.session.persistent.Persistent;
import com.douglei.sessions.session.persistent.PersistentFactory;
import com.douglei.sessions.session.persistent.PersistentObjectIdentity;
import com.douglei.sessions.session.persistent.RepeatPersistentObjectException;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class SessionImpl extends AbstractSession implements Session {
	private static final Logger logger = LoggerFactory.getLogger(SessionImpl.class);
	private Map<String, Map<PersistentObjectIdentity, Object>> insertPersistentObjectCache= new HashMap<String, Map<PersistentObjectIdentity,Object>>();
	private Map<String, Map<PersistentObjectIdentity, Object>> updatePersistentObjectCache= new HashMap<String, Map<PersistentObjectIdentity,Object>>();
	private Map<String, Map<PersistentObjectIdentity, Object>> deletePersistentObjectCache= new HashMap<String, Map<PersistentObjectIdentity,Object>>();
	
	public SessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
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
		
		Persistent persistent = PersistentFactory.buildPersistent(mapping.getMetadata(), object);
		putInsertPersistentObjectCache(persistent);
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
		
		Persistent persistent = PersistentFactory.buildPersistent(mapping.getMetadata(), propertyMap);
		putInsertPersistentObjectCache(persistent);
	}
	
	/**
	 * 将要保存的持久化对象放到缓存中
	 * @param persistent
	 */
	private void putInsertPersistentObjectCache(Persistent persistent) {
		Map<PersistentObjectIdentity, Object> cache = getCache(persistent.getCode(), insertPersistentObjectCache);
		
		PersistentObjectIdentity id = persistent.getPersistentObjectIdentity();
		if(cache.containsKey(id)) {
			logger.error("保存的对象[{}]出现重复的id值:{}", persistent.getCode(), id.toString());
			throw new RepeatPersistentObjectException("保存的对象["+persistent.getCode()+"]出现重复的id值:" + id.toString());
		}
		cache.put(id, persistent);
	}
	
	/**
	 * 获取缓存集合
	 * @param code
	 * @param cacheMap
	 * @return
	 */
	private Map<PersistentObjectIdentity, Object> getCache(String code, Map<String, Map<PersistentObjectIdentity, Object>> cacheMap){
		Map<PersistentObjectIdentity, Object> cache = cacheMap.get(code);
		if(cache == null) {
			cache = new HashMap<PersistentObjectIdentity, Object>();
			cacheMap.put(code, cache);
		}
		return cache;
	}

	@Override
	protected void flush() {
		
	}

	@Override
	public void close() {
		flush();
		super.close();
	}
}
