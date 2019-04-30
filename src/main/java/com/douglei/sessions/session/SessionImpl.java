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
	
	public SessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
	}

	@Override
	public void save(Object object) {
		Mapping mapping = getMapping(object, "save");
		Persistent persistent = PersistentFactory.buildPersistent(mapping.getMetadata(), object);
		putInsertPersistentObjectCache(persistent);
	}
	
	@Override
	public void save(String code, Map<String, Object> propertyMap) {
		Mapping mapping = getMapping(code, "save");
		Persistent persistent = PersistentFactory.buildPersistent(mapping.getMetadata(), propertyMap);
		putInsertPersistentObjectCache(persistent);
	}
	
	@Override
	public void update(Object object) {
		Mapping mapping = getMapping(object, "update");
		Persistent persistent = PersistentFactory.buildPersistent(mapping.getMetadata(), object);
		putUpdatePersistentObjectCache(persistent);
	}
	
	@Override
	public void update(String code, Map<String, Object> propertyMap) {
		Mapping mapping = getMapping(code, "update");
		Persistent persistent = PersistentFactory.buildPersistent(mapping.getMetadata(), propertyMap);
		putUpdatePersistentObjectCache(persistent);
	}
	
	@Override
	public void delete(Object object) {
		Mapping mapping = getMapping(object, "delete");
		Persistent persistent = PersistentFactory.buildPersistent(mapping.getMetadata(), object);
		putDeletePersistentObjectCache(persistent);
	}

	@Override
	public void delete(String code, Map<String, Object> propertyMap) {
		Mapping mapping = getMapping(code, "delete");
		Persistent persistent = PersistentFactory.buildPersistent(mapping.getMetadata(), propertyMap);
		putDeletePersistentObjectCache(persistent);
	}
	
	/**
	 * 将要【保存的持久化对象】放到缓存中
	 * @param persistent
	 */
	private void putInsertPersistentObjectCache(Persistent persistent) {
		String code = persistent.getCode();
		Map<PersistentObjectIdentity, Object> cache = getCache(code, insertPersistentObjectCache);
		
		PersistentObjectIdentity id = persistent.getId();
		if(id.isNull()) {
			throw new NullPointerException("保存的对象["+code+"], id值不能为空");
		}
		if(cache.containsKey(id)) {
			throw new RepeatPersistentObjectException("保存的对象["+code+"]出现重复的id值:" + id.toString());
		}
		cache.put(id, persistent);
	}
	
	/**
	 * 将要【修改的持久化对象】放到缓存中
	 * @param persistent
	 */
	private void putUpdatePersistentObjectCache(Persistent persistent) {
		String code = persistent.getCode();
		Map<PersistentObjectIdentity, Object> cache = getCache(code, updatePersistentObjectCache);
		
		PersistentObjectIdentity id = persistent.getId();
		if(id.isNull()) {
			throw new NullPointerException("修改的对象["+code+"], id值不能为空");
		}
		if(logger.isDebugEnabled()) {
			if(cache.containsKey(id)) {
				logger.debug("修改的对象[{}]出现重复的id值:{}", code, id.toString());
				logger.debug("源对象信息为: {}", cache.get(id).toString());
				logger.debug("本次修改的对象信息为: {}", persistent.toString());
				logger.debug("本次对象信息, 覆盖源对象信息");
			}
		}
		cache.put(id, persistent);
	}
	
	/**
	 * 将要【删除的持久化对象】放到缓存中
	 * @param persistent
	 */
	private void putDeletePersistentObjectCache(Persistent persistent) {
		String code = persistent.getCode();
		Map<PersistentObjectIdentity, Object> cache = getCache(code, deletePersistentObjectCache);
		
		PersistentObjectIdentity id = persistent.getId();
		if(id.isNull()) {
			throw new NullPointerException("删除的对象["+code+"], id值不能为空");
		}
		if(cache.containsKey(id)) {
			if(logger.isDebugEnabled()) {
				logger.debug("删除的对象[{}]出现重复的id值:{}", code, id.toString());
				logger.debug("源对象信息为: {}", cache.get(id).toString());
				logger.debug("本次删除的对象信息为: {}", persistent.toString());
				logger.debug("不将本次对象, 覆盖添加到缓存中");
			}
			return;
		}
		cache.put(id, persistent);
	}
	
	/**
	 * 获取mapping实例
	 * @param object
	 * @param description 传入调用该方法的方法名
	 * @return
	 */
	private Mapping getMapping(Object object, String description) {
		if(object == null) {
			throw new NullPointerException("要"+description+"的对象不能为空");
		}
		String code = object.getClass().getName();
		logger.debug("对实体对象{} 进行{}操作", code, description);
		
		Mapping mapping = mappingWrapper.getMapping(code);
		if(mapping == null) {
			throw new NullPointerException("不存在code为["+code+"]的映射");
		}
		return mapping;
	}
	
	/**
	 * 获取mapping实例
	 * @param code
	 * @param description 传入调用该方法的方法名
	 * @return
	 */
	private Mapping getMapping(String code, String description) {
		if(StringUtil.isEmpty(code)) {
			throw new NullPointerException("要"+description+"的对象的code值不能为空");
		}
		logger.debug("对code={} 的对象进行{}操作", code, description);
		
		Mapping mapping = mappingWrapper.getMapping(code);
		if(mapping == null) {
			throw new NullPointerException("不存在code为["+code+"]的映射");
		}
		return mapping;
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
