package com.douglei.sessions.session.table.impl;

import java.util.ArrayList;
import java.util.Collection;
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
import com.douglei.database.utils.NamingUtil;
import com.douglei.sessions.session.MappingMismatchingException;
import com.douglei.sessions.session.persistent.OperationState;
import com.douglei.sessions.session.persistent.PersistentObject;
import com.douglei.sessions.session.persistent.RepeatPersistentObjectException;
import com.douglei.sessions.session.persistent.execution.ExecutionHolder;
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
	private Map<String, Map<Identity, PersistentObject>> persistentObjectCache= new HashMap<String, Map<Identity, PersistentObject>>(16);
	
	public TableSessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
	}
	
	/**
	 * 获取缓存集合
	 * @param code
	 * @param cacheMap
	 * @return
	 */
	private Map<Identity, PersistentObject> getCache(String code){
		logger.debug("获取code={}的持久化缓存集合", code);
		Map<Identity, PersistentObject> cache = persistentObjectCache.get(code);
		if(cache == null) {
			cache = new HashMap<Identity, PersistentObject>();
			persistentObjectCache.put(code, cache);
		}
		return cache;
	}
	
	@Override
	public void save(Object object) {
		Mapping mapping = getMapping(object, "save");
		PersistentObject persistent = new TablePersistentObject((TableMetadata)mapping.getMetadata(), object, OperationState.CREATE);
		putInsertPersistentObjectCache(persistent);
	}
	
	@Override
	public void save(String code, Map<String, Object> propertyMap) {
		Mapping mapping = getMapping(code, "save");
		PersistentObject persistent = new TablePersistentObject((TableMetadata)mapping.getMetadata(), propertyMap, OperationState.CREATE);
		putInsertPersistentObjectCache(persistent);
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
		cache.put(id, persistent);
	}
	
	/**
	 * 从object中解析出Identity实例
	 * @param object
	 * @param tableMetadata
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Identity getIdentity(Object object, TableMetadata tableMetadata) {
		Map<String, Object> idMap = null;
		if(object instanceof Map) {
			logger.debug("object is Map type, 从该map中, 筛选出[ID列]的数据信息");
			idMap = filterPrimaryKeyColumnMetadatasPropertyMap(tableMetadata, (Map<String, Object>)object);
		}else {
			logger.debug("object is Object type [{}], 从该object中, 通过java内省机制, 获取[ID列]的数据信息", object.getClass());
			idMap = IntrospectorUtil.getProperyValues(object, tableMetadata.getPrimaryKeyColumnMetadataCodes());
		}
		
		Object id;
		if(tableMetadata.getPrimaryKeyColumnMetadataCodes().size() == 1) {
			id = idMap.entrySet().iterator().next().getValue();
		}else {
			id = idMap;
		}
		return new Identity(id);
	}
	
	/**
	 * 从originPropertyMap集合中, 筛选出<b>ID</b>列的数据信息
	 * @param tableMetadata
	 * @param originPropertyMap 
	 * @return
	 */
	private Map<String, Object> filterPrimaryKeyColumnMetadatasPropertyMap(TableMetadata tableMetadata, Map<String, Object> originPropertyMap) {
		Set<String> primaryKeyColumnMetadataCodes = tableMetadata.getPrimaryKeyColumnMetadataCodes();
		int primaryKeyColumnSize = primaryKeyColumnMetadataCodes.size();
		Map<String, Object> resultPropertyMap = new HashMap<String, Object>(primaryKeyColumnSize);
		
		int index = 1;
		Set<String> originPropertyMapKeys = originPropertyMap.keySet();
		for (String originPMkey : originPropertyMapKeys) {
			if(tableMetadata.isPrimaryKeyColumn(originPMkey)) {
				resultPropertyMap.put(originPMkey, originPropertyMap.get(originPMkey));
				if(index == primaryKeyColumnSize) {
					break;
				}
				index++;
			}
		}
		return resultPropertyMap;
	}

	@Override
	public void update(Object object) {
		Mapping mapping = getMapping(object, "update");
		putUpdatePersistentObjectCache(object, (TableMetadata)mapping.getMetadata(), getCache(mapping.getCode()));
	}
	
	@Override
	public void update(String code, Map<String, Object> propertyMap) {
		Mapping mapping = getMapping(code, "update");
		putUpdatePersistentObjectCache(propertyMap, (TableMetadata)mapping.getMetadata(), getCache(mapping.getCode()));
	}
	
	/**
	 * 将要【修改的持久化对象】放到缓存中
	 * @param object
	 * @param tableMetadata
	 * @param cache
	 */
	private void putUpdatePersistentObjectCache(Object object, TableMetadata tableMetadata, Map<Identity, PersistentObject> cache) {
		Identity id = getIdentity(object, tableMetadata);
		if(cache.containsKey(id)) {
			logger.debug("缓存中存在要修改的数据持久化对象");
			PersistentObject persistentObject = cache.get(id);
			switch(persistentObject.getOperationState()) {
				case CREATE:
				case UPDATE:
					logger.debug("将{}状态的数据, 修改originObject数据后, 不对状态进行修改, 完成update", persistentObject.getOriginObject());
					persistentObject.setOriginObject(object);
					return;
				case DELETE:
					throw new IllegalArgumentException("缓存中的持久化对象["+persistentObject.toString()+"]已经被删除, 无法进行update");
			}
		}else {
			logger.debug("缓存中不存在要修改的数据持久化对象");
			PersistentObject persistentObject = new TablePersistentObject(tableMetadata, object, OperationState.UPDATE);
			persistentObject.setIdentity(id);
			cache.put(id, persistentObject);
		}
	}

	@Override
	public void delete(Object object) {
		Mapping mapping = getMapping(object, "delete");
		putDeletePersistentObjectCache(object, (TableMetadata)mapping.getMetadata(), getCache(mapping.getCode()));
	}

	@Override
	public void delete(String code, Map<String, Object> propertyMap) {
		Mapping mapping = getMapping(code, "delete");
		putDeletePersistentObjectCache(propertyMap, (TableMetadata)mapping.getMetadata(), getCache(mapping.getCode()));
	}
	
	/**
	 * 将要【删除的持久化对象】放到缓存中
	 * @param object
	 * @param tableMetadata
	 * @param cache
	 */
	private void putDeletePersistentObjectCache(Object object, TableMetadata tableMetadata, Map<Identity, PersistentObject> cache) {
		Identity id = getIdentity(object, tableMetadata);
		if(cache.containsKey(id)) {
			logger.debug("缓存中存在要删除的数据持久化对象");
			PersistentObject persistentObject = cache.get(id);
			switch(persistentObject.getOperationState()) {
				case CREATE:
					logger.debug("将create状态的数据, 从缓存中移除, 完成delete");
					cache.remove(id);
					return;
				case UPDATE:
					logger.debug("将update状态的数据, 改成delete状态, 完成delete");
					persistentObject.setOperationState(OperationState.DELETE);
					return;
				case DELETE:
					throw new IllegalArgumentException("缓存中的持久化对象["+persistentObject.toString()+"]已经被删除, 无法进行delete");
			}
		}else {
			logger.debug("缓存中不存在要删除的数据持久化对象");
			PersistentObject persistentObject = new TablePersistentObject(tableMetadata, object, OperationState.DELETE);
			persistentObject.setIdentity(id);
			cache.put(id, persistentObject);
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
		flushPersistentObjectCache();
	}

	// 将持久化缓存中的数据刷新出去
	private void flushPersistentObjectCache() {
		if(persistentObjectCache.size() > 0) {
			Map<Identity, PersistentObject> map = null;
			Collection<PersistentObject> persistentObjects = null;
			ExecutionHolder executionHolder = null;
			Set<String> codes = persistentObjectCache.keySet();
			for (String code : codes) {
				map = persistentObjectCache.get(code);
				if(map.size() > 0) {
					persistentObjects = map.values();
					for(PersistentObject persistentObject: persistentObjects) {
						executionHolder = persistentObject.getExecutionHolder();
						if(executionHolder == null) {
							if(logger.isDebugEnabled()) {
								logger.debug("执行state={}, persistentObject={}, 获取的ExecutionHolder实例为null", persistentObject.getOperationState(), persistentObject.toString());
							}
							continue;
						}
						if(logger.isDebugEnabled()) {
							logger.debug("执行state={}, persistentObject={}, 获取的ExecutionHolder {} = {}", persistentObject.getOperationState(), persistentObject.toString(), executionHolder.getClass(), executionHolder.toString());
						}
						super.executeUpdate(executionHolder.getSql(), executionHolder.getParameters());
					}
				}
			}
		}
	}

	@Override
	public void close() {
		flush();
		super.close();
	}

	private TableMetadata getTableMetadata(String code) {
		Mapping mapping = mappingWrapper.getMapping(code);
		if(mapping == null) {
			throw new NullPointerException("不存在code为["+code+"]的映射");
		}
		if(mapping.getMappingType() != MappingType.TABLE) {
			throw new MappingMismatchingException("传入code=["+code+"], 获取的mapping不是["+MappingType.TABLE+"]类型");
		}
		logger.debug("获取code={} tablemetadata", code);
		return (TableMetadata) mapping.getMetadata();
	}
	
	@Override
	public <T> List<T> query(Class<T> targetClass, String sql, List<Object> parameters) {
		List<Map<String, Object>> listMap = query(sql, parameters);
		if(listMap.size() > 0) {
			TableMetadata tableMetadata = getTableMetadata(targetClass.getName());
			List<T> listT = new ArrayList<T>(listMap.size());
			for (Map<String, Object> map : listMap) {
				listT.add(map2Class(targetClass, map, tableMetadata));
			}
			return listT;
		}
		return null;
	}

	@Override
	public <T> T uniqueQuery(Class<T> targetClass, String sql, List<Object> parameters) {
		Map<String, Object> map = uniqueQuery(sql, parameters);
		if(map.size() > 0) {
			TableMetadata tableMetadata = getTableMetadata(targetClass.getName());
			return map2Class(targetClass, map, tableMetadata);
		}
		return null;
	}
	
	// 将map转换为类
	@SuppressWarnings("unchecked")
	private <T> T map2Class(Class<T> targetClass, Map<String, Object> map, TableMetadata tableMetadata) {
		if(map.size() == 0) {
			return null;
		}
		if(tableMetadata == null || tableMetadata.classNameIsNull()) { // 没有配置映射, 或没有配置映射的类, 则将列名转换为属性名
			map = mapKey2PropertyName(map); 
		}else {
			map = mapKey2MappingPropertyName(map, tableMetadata); // 配置了类映射, 要从映射中获取映射的属性
		}
		return (T) IntrospectorUtil.setProperyValues(ConstructorUtil.newInstance(targetClass), map);
	}

	// 将map的key, 由列名转换成属性名
	private Map<String, Object> mapKey2PropertyName(Map<String, Object> map) {
		Map<String, Object> targetMap = new HashMap<String, Object>(map.size());
		Set<String> keys = map.keySet();
		for (String key : keys) {
			targetMap.put(NamingUtil.columnName2PropertyName(key), map.get(key));
		}
		return targetMap;
	}
	
	// 将map的key, 由列名转换成映射的属性名
	private Map<String, Object> mapKey2MappingPropertyName(Map<String, Object> map, TableMetadata tableMetadata) {
		Map<String, Object> targetMap = new HashMap<String, Object>(map.size());
		
		ColumnMetadata column = null;
		Set<String> codes = tableMetadata.getColumnMetadataCodes();
		for (String code : codes) {
			column = tableMetadata.getColumnMetadata(code);
			targetMap.put(column.getPropertyName(), map.get(column.getName().toUpperCase()));
		}
		return targetMap;
	}
}
