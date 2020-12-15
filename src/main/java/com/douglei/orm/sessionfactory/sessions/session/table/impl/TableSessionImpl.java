package com.douglei.orm.sessionfactory.sessions.session.table.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.ConnectionWrapper;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sessionfactory.sessions.SessionExecutionException;
import com.douglei.orm.sessionfactory.sessions.session.execute.ExecuteHandler;
import com.douglei.orm.sessionfactory.sessions.session.table.TableSession;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.AlreadyDeletedException;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.OperationState;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.PersistentObject;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.RepeatedPersistentObjectException;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.UnsupportUpdatePersistentWithoutPrimaryKeyException;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.id.Identity;
import com.douglei.orm.sessionfactory.sessions.sqlsession.impl.SqlSessionImpl;
import com.douglei.orm.sql.ReturnID;
import com.douglei.orm.sql.statement.InsertResult;
import com.douglei.tools.utils.reflect.ConstructorUtil;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class TableSessionImpl extends SqlSessionImpl implements TableSession {
	private static final Logger logger = LoggerFactory.getLogger(TableSessionImpl.class);
	
	private boolean enableTalbeSessionCache;// 是否启用TableSession缓存
	private final Map<String, TableMetadata> tableMetadataCache = new HashMap<String, TableMetadata>(8);
	private Map<String, Map<Identity, PersistentObject>> persistentObjectCache;
	
	public TableSessionImpl(ConnectionWrapper connection, Environment environment) {
		super(connection, environment);
		this.enableTalbeSessionCache = environment.getEnvironmentProperty().enableTableSessionCache();
		logger.debug("是否开启TableSession缓存: {}", enableTalbeSessionCache);
		if(enableTalbeSessionCache) 
			persistentObjectCache= new HashMap<String, Map<Identity, PersistentObject>>(8);
	}
	
	/**
	 * 获取缓存集合
	 * @param code
	 * @param cacheMap
	 * @return
	 */
	private Map<Identity, PersistentObject> getCache(String code){
		if(enableTalbeSessionCache) {
			logger.debug("获取code={}的持久化缓存集合", code);
			Map<Identity, PersistentObject> cache = persistentObjectCache.get(code);
			if(cache == null) {
				cache = new HashMap<Identity, PersistentObject>(8);
				persistentObjectCache.put(code, cache);
			}
			return cache;
		}
		return null;
	}
	
	private TableMetadata getTableMetadata(String code) {
		TableMetadata tableMetadata = null;
		if(tableMetadataCache.isEmpty() || (tableMetadata = tableMetadataCache.get(code)) == null) {
			tableMetadata = mappingHandler.getTableMetadata(code);
			tableMetadataCache.put(code, tableMetadata);
		}
		return tableMetadata;
	}
	
	/**
	 * 将要【保存的持久化对象】放到缓存中
	 * @param persistent
	 */
	private void putInsertPersistentObjectCache(PersistentObject persistent) {
		if(enableTalbeSessionCache) {
			String code = persistent.getTableMetadata().getCode();
			Map<Identity, PersistentObject> cache = getCache(code);
			
			if(!cache.isEmpty() && cache.containsKey(persistent.getId())) 
				throw new RepeatedPersistentObjectException("保存的对象["+code+"]出现重复的id值: existsObject=["+cache.get(persistent.getId())+"], thisObject=["+persistent+"]");
			cache.put(persistent.getId(), persistent);
		}else {
			executePersistentObject(persistent);
		}
	}
	
	private void save_(TableMetadata table, Object object) {
		PersistentObject persistent = new PersistentObject(table, object, OperationState.INSERT, false);
		putInsertPersistentObjectCache(persistent);
	}
	
	@Override
	public void save(Object object) {
		save_(getTableMetadata(object.getClass().getName()), object);
	}
	
	@Override
	public void save(List<? extends Object> objects) {
		TableMetadata table = getTableMetadata(objects.get(0).getClass().getName());
		objects.forEach(object -> save_(table, object));
	}
	
	@Override
	public void save(String tableName, Map<String, Object> objectMap) {
		save_(getTableMetadata(tableName.toUpperCase()), objectMap);
	}
	
	@Override
	public void save(String tableName, List<Map<String, Object>> objectMaps) {
		TableMetadata table = getTableMetadata(tableName.toUpperCase());
		objectMaps.forEach(objectMap -> save_(table, objectMap));
	}
	
	
	/**
	 * 将要【修改的持久化对象】放到缓存中
	 * @param object
	 * @param tableMetadata
	 * @param updateNullValue
	 * @param cache
	 */
	private void putUpdatePersistentObjectCache(Object object, TableMetadata tableMetadata, boolean updateNullValue, Map<Identity, PersistentObject> cache) {
		if(tableMetadata.getPrimaryKeyColumns_() == null) 
			throw new UnsupportUpdatePersistentWithoutPrimaryKeyException(tableMetadata.getCode()); // 因为没法区分数据中哪些应该被update set, 哪些应该做where条件
		
		PersistentObject persistentObject = new PersistentObject(tableMetadata, object, OperationState.UPDATE, updateNullValue);
		if(enableTalbeSessionCache) {
			if(!cache.isEmpty() && cache.containsKey(persistentObject.getId())) {
				logger.debug("缓存中存在要修改的数据持久化对象");
				persistentObject = cache.get(persistentObject.getId());
				switch(persistentObject.getOperationState()) {
					case INSERT:
					case UPDATE:
						persistentObject.setOriginObject(object);
						persistentObject.setUpdateNullValue(updateNullValue);
						break;
					case DELETE:
						throw new AlreadyDeletedException("持久化对象["+persistentObject.toString()+"]已经被删除, 无法进行update");
				}
			}else {
				logger.debug("缓存中不存在要修改的数据持久化对象");
				cache.put(persistentObject.getId(), persistentObject);
			}
		}else {
			executePersistentObject(persistentObject);
		}
	}
	
	// update方法的内部实现
	private void update_(TableMetadata table, Object object, boolean updateNullValue) {
		putUpdatePersistentObjectCache(object, table, updateNullValue, getCache(table.getCode()));
	}
	
	@Override
	public void update(Object object, boolean updateNullValue) {
		update_(getTableMetadata(object.getClass().getName()), object, updateNullValue);
	}
	
	@Override
	public void update(List<Object> objects, boolean updateNullValue) {
		TableMetadata table = getTableMetadata(objects.get(0).getClass().getName());
		objects.forEach(object -> update_(table, object, updateNullValue));
	}
	
	@Override
	public void update(String tableName, Map<String, Object> objectMap, boolean updateNullValue) {
		update_(getTableMetadata(tableName.toUpperCase()), objectMap, updateNullValue);
	}
	
	@Override
	public void update(String tableName, List<Map<String, Object>> objectMaps, boolean updateNullValue) {
		TableMetadata table = getTableMetadata(tableName.toUpperCase());
		objectMaps.forEach(objectMap -> update_(table, objectMap, updateNullValue));
	}
	

	/**
	 * 将要【删除的持久化对象】放到缓存中
	 * @param object
	 * @param tableMetadata
	 * @param cache
	 */
	private void putDeletePersistentObjectCache(Object object, TableMetadata tableMetadata, Map<Identity, PersistentObject> cache) {
		PersistentObject persistentObject = new PersistentObject(tableMetadata, object, OperationState.DELETE, false);
		if(enableTalbeSessionCache) {
			if(!cache.isEmpty() && cache.containsKey(persistentObject.getId())) {
				logger.debug("缓存中存在要删除的数据持久化对象");
				persistentObject = cache.get(persistentObject.getId());
				switch(persistentObject.getOperationState()) {
					case INSERT:
						logger.debug("将create状态的数据直接从cache中移除, 完成delete");
						cache.remove(persistentObject.getId());
						return;
					case UPDATE:
						logger.debug("将update状态的数据, 改成delete状态, 完成delete");
						persistentObject.setOperationState(OperationState.DELETE);
						return;
					case DELETE:
						throw new AlreadyDeletedException("持久化对象["+persistentObject.toString()+"]已经被删除, 无法进行delete");
				}
			}else {
				logger.debug("缓存中不存在要删除的数据持久化对象");
				cache.put(persistentObject.getId(), persistentObject);
			}
		}else {
			executePersistentObject(persistentObject);
		}
	}
	
	private void delete_(TableMetadata table, Object object) {
		putDeletePersistentObjectCache(object, table, getCache(table.getCode()));
	}

	@Override
	public void delete(Object object) {
		delete_(getTableMetadata(object.getClass().getName()), object);
	}
	
	@Override
	public void delete(List<Object> objects) {
		TableMetadata table = getTableMetadata(objects.get(0).getClass().getName());
		objects.forEach(object -> delete_(table, object));
	}
	
	@Override
	public void delete(String tableName, Map<String, Object> objectMap) {
		delete_(getTableMetadata(tableName.toUpperCase()), objectMap);
	}
	
	@Override
	public void delete(String tableName, List<Map<String, Object>> objectMaps) {
		TableMetadata table = getTableMetadata(tableName.toUpperCase());
		objectMaps.forEach(objectMap -> delete_(table, objectMap));
	}

		
	private void flushPersistentObjectCache() throws SessionExecutionException {
		if(!tableMetadataCache.isEmpty())
			tableMetadataCache.clear();
		
		if(enableTalbeSessionCache && !persistentObjectCache.isEmpty()) {
			Map<Identity, PersistentObject> map = null;
			Set<String> codes = persistentObjectCache.keySet();
			try {
				for (String code : codes) {
					map = persistentObjectCache.get(code);
					if(!map.isEmpty()) {
						for(PersistentObject persistentObject: map.values()) {
							executePersistentObject(persistentObject);
						}
					}
				}
			} catch(SessionExecutionException see){
				throw see;
			}finally {
				persistentObjectCache.clear();
			}
		}
	}
	
	private void executePersistentObject(PersistentObject persistentObject) throws SessionExecutionException {
		ExecuteHandler executeHandler = persistentObject.getExecuteHandler();
		if(persistentObject.getOperationState() == OperationState.INSERT && persistentObject.getTableMetadata().getPrimaryKeySequence() != null) {
			// 如果是保存表数据, 且使用了序列作为主键值
			TableMetadata tableMetadata = persistentObject.getTableMetadata();
			InsertResult result = super.executeInsert(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters(), new ReturnID(tableMetadata.getPrimaryKeySequence().getName()));
			// 将执行insert语句后生成的序列值, 赋给源实例
			IntrospectorUtil.setProperyValue(persistentObject.getOriginObject(), tableMetadata.getPrimaryKeyColumns_().keySet().iterator().next(), result.getId());
		}else {
			super.executeUpdate(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
		}
	}
	
	@Override
	protected <T> List<T> listMap2listClass(Class<T> targetClass, List<Map<String, Object>> resultListMap) {
		TableMetadata tableMetadata = getTableMetadata(targetClass.getName());
		List<T> listT = new ArrayList<T>(resultListMap.size());
		for (Map<String, Object> resultMap : resultListMap) {
			listT.add(map2Class(tableMetadata, targetClass, resultMap));
		}
		return listT;
	}

	@Override
	protected <T> T map2Class(Class<T> targetClass, Map<String, Object> resultMap) {
		return map2Class(getTableMetadata(targetClass.getName()), targetClass, resultMap);
	}
	
	/**
	 * 将map转换为类
	 * @param tableMetadata
	 * @param targetClass
	 * @param resultMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T map2Class(TableMetadata tableMetadata, Class<T> targetClass, Map<String, Object> resultMap) {
		ColumnMetadata column = null;
		Set<String> codes = tableMetadata.getColumns_().keySet();
		for (String code : codes) {
			column = tableMetadata.getColumns_().get(code);
			resultMap.put(column.getCode(), resultMap.remove(column.getName()));
		}
		return (T) IntrospectorUtil.setProperyValues(ConstructorUtil.newInstance(targetClass), resultMap);
	}
	
	@Override
	public void close() {
		if(!isClosed) {
			if(logger.isDebugEnabled()) 
				logger.debug("close {}", getClass().getName());
			
			try {
				flushPersistentObjectCache();
			} catch(SessionExecutionException e){
				throw e;
			}finally {
				super.close();
			}
		}
	}
}
