package com.douglei.orm.sessions.session.table.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.sql.ConnectionWrapper;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.orm.sessions.session.MappingMismatchingException;
import com.douglei.orm.sessions.session.execution.ExecutionHolder;
import com.douglei.orm.sessions.session.table.TableSession;
import com.douglei.orm.sessions.session.table.impl.persistent.OperationState;
import com.douglei.orm.sessions.session.table.impl.persistent.PersistentObject;
import com.douglei.orm.sessions.session.table.impl.persistent.RepeatedPersistentObjectException;
import com.douglei.orm.sessions.session.table.impl.persistent.id.Identity;
import com.douglei.orm.sessions.sqlsession.impl.SqlSessionImpl;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class TableSessionImpl extends SqlSessionImpl implements TableSession {
	private static final Logger logger = LoggerFactory.getLogger(TableSessionImpl.class);
	
	private boolean enableTalbeSessionCache;// 是否启用TableSession缓存
	private Map<String, Map<Identity, PersistentObject>> persistentObjectCache;
	
	public TableSessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
		this.enableTalbeSessionCache = environmentProperty.getEnableTableSessionCache();
		logger.debug("是否开启TableSession缓存: {}", enableTalbeSessionCache);
		if(enableTalbeSessionCache) {
			persistentObjectCache= new HashMap<String, Map<Identity, PersistentObject>>(8);
		}
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
	
	@Override
	public void save(Object object) {
		Mapping mapping = getTableMapping(object, "save");
		PersistentObject persistent = new PersistentObject((TableMetadata)mapping.getMetadata(), object, OperationState.CREATE);
		putInsertPersistentObjectCache(persistent);
	}
	
	@Override
	public void save(String code, Map<String, Object> propertyMap) {
		Mapping mapping = getTableMapping(code, "save");
		PersistentObject persistent = new PersistentObject((TableMetadata)mapping.getMetadata(), propertyMap, OperationState.CREATE);
		putInsertPersistentObjectCache(persistent);
	}
	
	/**
	 * 将要【保存的持久化对象】放到缓存中
	 * @param persistent
	 */
	private void putInsertPersistentObjectCache(PersistentObject persistent) {
		if(enableTalbeSessionCache) {
			String code = persistent.getCode();
			Map<Identity, PersistentObject> cache = getCache(code);
			
			if(cache.containsKey(persistent.getId())) {
				throw new RepeatedPersistentObjectException("保存的对象["+code+"]出现重复的id值:" + persistent.getId().toString());
			}
			cache.put(persistent.getId(), persistent);
		}else {
			executePersistentObject(persistent);
		}
	}
	
	@Override
	public void update(Object object) {
		Mapping mapping = getTableMapping(object, "update");
		putUpdatePersistentObjectCache(object, (TableMetadata)mapping.getMetadata(), getCache(mapping.getCode()));
	}
	
	@Override
	public void update(String code, Map<String, Object> propertyMap) {
		Mapping mapping = getTableMapping(code, "update");
		putUpdatePersistentObjectCache(propertyMap, (TableMetadata)mapping.getMetadata(), getCache(mapping.getCode()));
	}
	
	/**
	 * 将要【修改的持久化对象】放到缓存中
	 * @param object
	 * @param tableMetadata
	 * @param cache
	 */
	private void putUpdatePersistentObjectCache(Object object, TableMetadata tableMetadata, Map<Identity, PersistentObject> cache) {
		if(!tableMetadata.existsPrimaryKey()) {
			throw new UnsupportUpdatePersistentWithoutPrimaryKeyException(tableMetadata.getCode());
		}
		PersistentObject persistentObject = new PersistentObject(tableMetadata, object, OperationState.UPDATE);
		if(enableTalbeSessionCache) {
			if(cache.containsKey(persistentObject.getId())) {
				logger.debug("缓存中存在要修改的数据持久化对象");
				persistentObject = cache.get(persistentObject.getId());
				switch(persistentObject.getOperationState()) {
				case CREATE:
				case UPDATE:
					if(logger.isDebugEnabled()) {
						logger.debug("将{}状态的数据, 修改originObject数据后, 不对状态进行修改, 完成update", persistentObject.getOriginObject());
					}
					persistentObject.setOriginObject(object);
					return;
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

	@Override
	public void delete(Object object) {
		Mapping mapping = getTableMapping(object, "delete");
		putDeletePersistentObjectCache(object, (TableMetadata)mapping.getMetadata(), getCache(mapping.getCode()));
	}

	@Override
	public void delete(String code, Map<String, Object> propertyMap) {
		Mapping mapping = getTableMapping(code, "delete");
		putDeletePersistentObjectCache(propertyMap, (TableMetadata)mapping.getMetadata(), getCache(mapping.getCode()));
	}
	
	/**
	 * 将要【删除的持久化对象】放到缓存中
	 * @param object
	 * @param tableMetadata
	 * @param cache
	 */
	private void putDeletePersistentObjectCache(Object object, TableMetadata tableMetadata, Map<Identity, PersistentObject> cache) {
		PersistentObject persistentObject = new PersistentObject(tableMetadata, object, OperationState.DELETE);
		if(enableTalbeSessionCache) {
			if(cache.containsKey(persistentObject.getId())) {
				logger.debug("缓存中存在要删除的数据持久化对象");
				persistentObject = cache.get(persistentObject.getId());
				switch(persistentObject.getOperationState()) {
					case CREATE:
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
		
	/**
	 * 获取mapping实例
	 * @param object
	 * @param description 传入调用该方法的方法名
	 * @return
	 */
	private Mapping getTableMapping(Object object, String description) {
		if(object == null) {
			throw new NullPointerException("要"+description+"的对象不能为空");
		}
		String code = object.getClass().getName();
		logger.debug("对实体对象{} 进行{}操作", code, description);
		return getTableMapping(code);
	}
	
	/**
	 * 获取mapping实例
	 * @param code
	 * @param description 传入调用该方法的方法名
	 * @return
	 */
	private Mapping getTableMapping(String code, String description) {
		if(StringUtil.isEmpty(code)) {
			throw new NullPointerException("要"+description+"的对象的code值不能为空");
		}
		logger.debug("对code={} 的对象进行{}操作", code, description);
		return getTableMapping(code);
	}
	
	private Mapping getTableMapping(String code) {
		Mapping mapping = mappingWrapper.getMapping(code);
		if(mapping == null) {
			throw new NullPointerException("不存在code为["+code+"]的映射");
		}
		if(mapping.getMappingType() != MappingType.TABLE) {
			throw new MappingMismatchingException("传入code=["+code+"], 获取的mapping不是["+MappingType.TABLE+"]类型");
		}
		return mapping;
	}

	private void flushPersistentObjectCache() {
		if(enableTalbeSessionCache && persistentObjectCache.size() > 0) {
			Map<Identity, PersistentObject> map = null;
			Collection<PersistentObject> persistentObjects = null;
			Set<String> codes = persistentObjectCache.keySet();
			for (String code : codes) {
				map = persistentObjectCache.get(code);
				if(map.size() > 0) {
					persistentObjects = map.values();
					for(PersistentObject persistentObject: persistentObjects) {
						executePersistentObject(persistentObject);
					}
				}
			}
			persistentObjectCache.clear();
		}
	}
	
	private void executePersistentObject(PersistentObject persistentObject) {
		ExecutionHolder executionHolder = persistentObject.getExecutionHolder();
		if(executionHolder == null) {
			if(logger.isDebugEnabled()) {
				logger.debug("执行state={}, persistentObject={}, 获取的ExecutionHolder实例为null", persistentObject.getOperationState(), persistentObject.toString());
			}
			return;
		}
		if(logger.isDebugEnabled()) {
			logger.debug("执行state={}, persistentObject={}, 获取的ExecutionHolder {} = {}", persistentObject.getOperationState(), persistentObject.toString(), executionHolder.getClass(), executionHolder.toString());
		}
		super.executeUpdate(executionHolder.getCurrentSql(), executionHolder.getCurrentParameters());
	}
	
	@Override
	public void close() {
		if(!isClosed) {
			if(logger.isDebugEnabled()) {
				logger.debug("close {}", getClass().getName());
			}
			flushPersistentObjectCache();
			super.close();
		}
	}

	private TableMetadata getTableMetadata(String code) {
		return (TableMetadata) getTableMapping(code).getMetadata();
	}
	
	@Override
	public <T> List<T> query(Class<T> targetClass, String sql, List<Object> parameters) {
		List<Map<String, Object>> listMap = super.query(sql, parameters);
		TableMetadata tableMetadata = getTableMetadata(targetClass.getName());
		return listMap2listClass(targetClass, listMap, tableMetadata);
	}
	
	@Override
	public <T> T uniqueQuery(Class<T> targetClass, String sql, List<Object> parameters) {
		Map<String, Object> map = uniqueQuery(sql, parameters);
		if(map != null && map.size() > 0) {
			TableMetadata tableMetadata = getTableMetadata(targetClass.getName());
			return map2Class(targetClass, map, tableMetadata);
		}
		return null;
	}
	
	@Override
	public <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String sql, List<Object> parameters) {
		if(logger.isDebugEnabled()) {
			logger.debug("开始执行分页查询, targetClass={}, pageNum={}, pageSize={}", targetClass.getName(), pageNum, pageSize);
		}
		PageResult<Map<String, Object>> pageResult = super.pageQuery(pageNum, pageSize, sql, parameters);
		PageResult<T> finalPageResult = new PageResult<T>(pageResult);
		
		TableMetadata tableMetadata = getTableMetadata(targetClass.getName());
		finalPageResult.setResultDatas(listMap2listClass(targetClass, pageResult.getResultDatas(), tableMetadata));
		if(logger.isDebugEnabled()) {
			logger.debug("分页查询的结果: {}", finalPageResult.toString());
		}
		return finalPageResult;
	}

	@Override
	public String getColumnNames(String code, String... excludeColumnNames) {
		TableMetadata tableMetadata = getTableMetadata(code);
		StringBuilder cns = new StringBuilder(tableMetadata.getDeclareColumns().size() * 30);
		tableMetadata.getDeclareColumns().forEach(column -> {
			if(unExcludeColumnName(column.getName(), excludeColumnNames)) {
				cns.append(", ").append(column.getName());
			}
		});
		if(cns.length() == 0) {
			throw new NullPointerException("在code=["+code+"]的表映射中, 没有匹配到任何列名, 请确保没有对过多的列名进行排除 (excludeColumnNames)");
		}
		logger.debug("得到的列名为 -> {}", cns);
		return cns.substring(1);
	}
	
	// 不排除指定的列名
	private boolean unExcludeColumnName(String columnName, String[] excludeColumnNames) {
		if(excludeColumnNames.length > 0) {
			for (String ecn : excludeColumnNames) {
				if(columnName.equalsIgnoreCase(ecn)) {
					return false;
				}
			}
		}
		return true;
	}
}
