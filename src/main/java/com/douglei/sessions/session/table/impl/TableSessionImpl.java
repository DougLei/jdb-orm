package com.douglei.sessions.session.table.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.Mapping;
import com.douglei.configuration.environment.mapping.MappingType;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.database.sql.statement.impl.Parameter;
import com.douglei.sessions.session.MappingMismatchingException;
import com.douglei.sessions.session.persistent.PersistentObject;
import com.douglei.sessions.session.persistent.RepeatPersistentObjectException;
import com.douglei.sessions.session.persistent.State;
import com.douglei.sessions.session.persistent.execution.ExecutionHolder;
import com.douglei.sessions.session.persistent.execution.ExecutionType;
import com.douglei.sessions.session.persistent.id.Identity;
import com.douglei.sessions.session.table.TableSession;
import com.douglei.sessions.session.table.impl.persistent.TablePersistentObject;
import com.douglei.sessions.sqlsession.SqlSessionImpl;
import com.douglei.utils.StringUtil;
import com.douglei.utils.reflect.ConstructorUtil;
import com.douglei.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class TableSessionImpl extends SqlSessionImpl implements TableSession {
	private static final Logger logger = LoggerFactory.getLogger(TableSessionImpl.class);
	private Map<String, Map<Identity, PersistentObject>> persistentObjectCache= new HashMap<String, Map<Identity, PersistentObject>>();
	private List<PersistentObject> insertCache = new ArrayList<PersistentObject>();
	private List<PersistentObject> updateCache = new ArrayList<PersistentObject>();
	private List<PersistentObject> deleteCache = new ArrayList<PersistentObject>();
	
	/**
	 * 获取缓存集合
	 * @param code
	 * @param cacheMap
	 * @return
	 */
	private Map<Identity, PersistentObject> getCache(String code){
		Map<Identity, PersistentObject> cache = persistentObjectCache.get(code);
		if(cache == null) {
			cache = new HashMap<Identity, PersistentObject>();
			persistentObjectCache.put(code, cache);
		}
		return cache;
	}
	
	public TableSessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
	}

	@Override
	public void save(Object object) {
		Mapping mapping = getMapping(object, "save");
		PersistentObject persistent = new TablePersistentObject((TableMetadata)mapping.getMetadata(), object);
		putInsertPersistentObjectCache(persistent);
	}
	
	@Override
	public void save(String code, Map<String, Object> propertyMap) {
		Mapping mapping = getMapping(code, "save");
		PersistentObject persistent = new TablePersistentObject((TableMetadata)mapping.getMetadata(), propertyMap);
		putInsertPersistentObjectCache(persistent);
	}
	
	@Override
	public void update(Object object) {
		Mapping mapping = getMapping(object, "update");
		PersistentObject persistent = new TablePersistentObject((TableMetadata)mapping.getMetadata(), object);
		putUpdatePersistentObjectCache(persistent);
	}
	
	@Override
	public void update(String code, Map<String, Object> propertyMap) {
		Mapping mapping = getMapping(code, "update");
		PersistentObject persistent = new TablePersistentObject((TableMetadata)mapping.getMetadata(), propertyMap);
		putUpdatePersistentObjectCache(persistent);
	}
	
	@Override
	public void delete(Object object) {
		Mapping mapping = getMapping(object, "delete");
		PersistentObject persistent = new TablePersistentObject((TableMetadata)mapping.getMetadata(), object);
		putDeletePersistentObjectCache(persistent);
	}

	@Override
	public void delete(String code, Map<String, Object> propertyMap) {
		Mapping mapping = getMapping(code, "delete");
		PersistentObject persistent = new TablePersistentObject((TableMetadata)mapping.getMetadata(), propertyMap);
		putDeletePersistentObjectCache(persistent);
	}
	
	/**
	 * 将要【保存的持久化对象】放到缓存中
	 * @param persistent
	 */
	private void putInsertPersistentObjectCache(PersistentObject persistent) {
		String code = persistent.getCode();
		Map<Identity, PersistentObject> cache = getCache(code);
		
		Identity id = persistent.getId();
		if(cache.containsKey(id)) {
			throw new RepeatPersistentObjectException("保存的对象["+code+"]出现重复的id值:" + id.toString());
		}
		
		persistent.setState(State.NEW_INSTANCE);
		cache.put(id, persistent);
		insertCache.add(persistent);
	}
	
	/**
	 * 将要【修改的持久化对象】放到缓存中
	 * @param persistent
	 */
	private void putUpdatePersistentObjectCache(PersistentObject persistent) {
		if(persistent.getState() == null) {
			throw new NullPointerException("修改对象时, 对象的State枚举属性值不能为空");
		}
		String code = persistent.getCode();
		Map<Identity, PersistentObject> cache = getCache(code);
		
		Identity id = persistent.getId();
		logger.debug("将persistentObjectCache集合中对应的持久化对象覆盖");
		cache.put(id, persistent);
		
		switch(persistent.getState()) {
			case NEW_INSTANCE:
				logger.debug("修改一个NEW_INSTANCE状态的数据对象, 将之前insertCache集合中的持久化对象覆盖");
				coverPersistentObjectListCache(insertCache, id, persistent);
				break;
			case PERSISTENT:
				logger.debug("修改一个PERSISTENT状态的数据对象, 将之前updateCache集合中的持久化对象覆盖");
				coverPersistentObjectListCache(updateCache, id, persistent);
				break;
		}
	}
	
	/**
	 * 覆盖cache集合中对应的持久化对象
	 * @param cacheList
	 * @param id
	 * @param persistent
	 */
	private void coverPersistentObjectListCache(List<PersistentObject> cacheList, Identity id, PersistentObject persistent) {
		if(logger.isDebugEnabled()) {
			logger.debug("覆盖的cache集合中对应的持久化对象");
			logger.debug("id={}", id.toString());
			logger.debug("新的persistent={}", persistent.toString());
		}
		if(cacheList.size() > 0) {
			PersistentObject oldPersistent = null ;
			for (int i = 0; i < cacheList.size(); i++) {
				if(cacheList.get(i).getId().equals(id)) {
					oldPersistent = cacheList.remove(i);
					break;
				}
			}
			if(oldPersistent != null && logger.isDebugEnabled()) {
				logger.debug("源persistent={}", oldPersistent.toString());
			}
		}
		cacheList.add(persistent);
	}
	
	/**
	 * 将要【删除的持久化对象】放到缓存中
	 * @param persistent
	 */
	private void putDeletePersistentObjectCache(PersistentObject persistent) {
		if(persistent.getState() == null) {
			throw new NullPointerException("删除对象时, 对象的State枚举属性值不能为空");
		}
		String code = persistent.getCode();
		Map<Identity, PersistentObject> cache = getCache(code);
		
		Identity id = persistent.getId();
		if(cache.containsKey(id)) {
			logger.debug("将persistentObjectCache集合中对应的持久化对象移除");
			cache.remove(id);
		}
		
		switch(persistent.getState()) {
			case NEW_INSTANCE:
				logger.debug("删除一个NEW_INSTANCE状态的数据对象, 将之前insertCache集合中的持久化对象移除");
				removePersistentObjectListCache(insertCache, id, persistent);
				break;
			case PERSISTENT:
				logger.debug("删除一个PERSISTENT状态的数据对象, 判断updateCache集合中是否有对该对象的修改, 如果有, 则将之前updateCache集合中的持久化对象移除");
				removePersistentObjectListCache(updateCache, id, persistent);
				deleteCache.add(persistent);
				break;
		}
	}
	
	/**
	 * 覆盖cache集合中对应的持久化对象
	 * @param cacheList
	 * @param id
	 * @param persistent
	 */
	private void removePersistentObjectListCache(List<PersistentObject> cacheList, Identity id, PersistentObject persistent) {
		if(logger.isDebugEnabled()) {
			logger.debug("移除的cache集合中对应的持久化对象");
			logger.debug("id={}", id.toString());
			logger.debug("persistent={}", persistent.toString());
		}
		if(cacheList.size() > 0) {
			for (int i = 0; i < cacheList.size(); i++) {
				if(cacheList.get(i).getId().equals(id)) {
					cacheList.remove(i);
					break;
				}
			}
		}
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
		return getMapping(code);
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
		return getMapping(code);
	}
	
	private Mapping getMapping(String code) {
		Mapping mapping = mappingWrapper.getMapping(code);
		if(mapping == null) {
			throw new NullPointerException("不存在code为["+code+"]的映射");
		}
		if(mapping.getMappingType() != MappingType.TABLE) {
			throw new MappingMismatchingException("传入code=["+code+"], 获取的mapping不是["+MappingType.TABLE+"]类型");
		}
		return mapping;
	}

	private void flush() {
		flushPersistentObject(insertCache, ExecutionType.INSERT);
		flushPersistentObject(updateCache, ExecutionType.UPDATE);
		flushPersistentObject(deleteCache, ExecutionType.DELETE);
	}

	private void flushPersistentObject(List<PersistentObject> cacheList, ExecutionType executionType) {
		if(cacheList.size() > 0) {
			ExecutionHolder executionHolder = null;
			for (PersistentObject persistentObject : cacheList) {
				executionHolder = persistentObject.getExecutionHolder(executionType);
				if(executionHolder == null) {
					if(logger.isDebugEnabled()) {
						logger.debug("执行executionType={}, persistentObject={}, 获取的ExecutionHolder实例为null", executionType.toString(), persistentObject.toString());
					}
					continue;
				}
				if(logger.isDebugEnabled()) {
					logger.debug("执行executionType={}, persistentObject={}, 获取的ExecutionHolder {} = {}", executionType.toString(), persistentObject.toString(), executionHolder.getClass(), executionHolder.toString());
				}
				super.executeUpdate(executionHolder.getSql(), executionHolder.getParameters());
			}
		}
	}

	@Override
	public void close() {
		flush();
		super.close();
	}

	@Override
	public <T> T queryById(Class<T> targetClass, Object id) {
		return queryById(targetClass, targetClass.getName(), id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T queryById(Class<T> targetClass, String code, Object id) {
		Identity identity = new Identity(id);
		
		// 先从缓存中取
		Object cacheObject = null;
		PersistentObject persistentObject = null;
		Map<Identity, PersistentObject> cache = getCache(code);
		if(cache != null && cache.size() > 0) {
			Set<Identity> identities = cache.keySet();
			for (Identity identity_ : identities) {
				if(identity.equals(identity_)) {
					persistentObject = cache.get(identity);
					cacheObject = persistentObject.getOriginObject();
				}
			}
		}
		if(cacheObject != null) {
			if(cacheObject instanceof Map) {
				cacheObject = IntrospectorUtil.setProperyValues(ConstructorUtil.newInstance(targetClass), (Map<String, Object>)cacheObject);
				persistentObject.setOriginObject(cacheObject);
			}
			return (T) cacheObject;
		}
		
		// 缓存中没有, 再去数据库中查询
		return queryById(targetClass, code, identity, cache);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T queryById(Class<T> targetClass, String code, Identity id, Map<Identity, PersistentObject> cache) {
		TableMetadata tableMetadata = (TableMetadata) getMapping(code, "queryById").getMetadata();
		
		
		StringBuilder selectSql = new StringBuilder();
		selectSql.append("select ");
		Set<String> codes = tableMetadata.getColumnMetadataCodes();
		int size = codes.size();
		int index = 1;
		ColumnMetadata column = null;
		for (String cd : codes) {
			column = tableMetadata.getColumnMetadata(cd);
			if(!column.isLazyload()) {
				selectSql.append(column.getName());
				if(index<size) {
					selectSql.append(",");
				}
			}
			index++;
		}
		
		
		
		selectSql.append(" from ").append(tableMetadata.getName()).append(" where ");
		List<ColumnMetadata> primaryKeyColumns = tableMetadata.getPrimaryKeyColumns();
		size = primaryKeyColumns.size();
		
		List<Parameter> parameters = new ArrayList<Parameter>(size);
		index = 1;
		for (ColumnMetadata pkColumn : primaryKeyColumns) {
			selectSql.append(pkColumn.getName()).append("=?");
			parameters.add(new Parameter(id.getPKValue(pkColumn.getCode()), pkColumn.getDataType()));
			
			if(index < size) {
				selectSql.append(" and ");
			}
			index++;
		}
		
		Map<String, Object> map = super.uniqueQuery(selectSql.toString(), parameters);
		if(map.size() > 0) {
			Object object = IntrospectorUtil.setProperyValues(ConstructorUtil.newInstance(targetClass), map);
			PersistentObject persistentObject = new TablePersistentObject(tableMetadata, object);
			persistentObject.setState(State.PERSISTENT);
			cache.put(id, persistentObject);
			
			return (T) object;
		}
		return null;
	}
	
}
