package com.douglei.orm.sessionfactory.sessions.session.table.impl;

import java.util.ArrayList;
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
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.table.UniqueConstraint;
import com.douglei.orm.core.sql.ConnectionWrapper;
import com.douglei.orm.core.sql.ReturnID;
import com.douglei.orm.core.sql.statement.InsertResult;
import com.douglei.orm.sessionfactory.data.validator.table.UniqueValidationResult;
import com.douglei.orm.sessionfactory.sessions.SessionExecutionException;
import com.douglei.orm.sessionfactory.sessions.session.MappingMismatchingException;
import com.douglei.orm.sessionfactory.sessions.session.execute.ExecuteHandler;
import com.douglei.orm.sessionfactory.sessions.session.table.TableSession;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.OperationState;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.PersistentObject;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.RepeatedPersistentObjectException;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.UniqueValue;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.id.Identity;
import com.douglei.orm.sessionfactory.sessions.sqlsession.impl.SqlSessionImpl;
import com.douglei.tools.utils.CollectionUtil;
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
	
	public TableSessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
		this.enableTalbeSessionCache = environmentProperty.enableTableSessionCache();
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
	
	private TableMetadata getTableMetadata(String code) {
		TableMetadata tm = null;
		if(tableMetadataCache.isEmpty() || (tm = tableMetadataCache.get(code)) == null) {
			Mapping mapping = mappingWrapper.getMapping(code);
			if(mapping.getMappingType() != MappingType.TABLE) {
				throw new MappingMismatchingException("传入code=["+code+"], 获取的mapping不是["+MappingType.TABLE+"]类型");
			}
			tm = (TableMetadata) mapping.getMetadata();
			tableMetadataCache.put(code, tm);
		}
		return tm;
	}
	
	
	/**
	 * 验证唯一值
	 * 需要开启 {enableTableSessionCache=true, enabledDataValidate=true} 这两个配置, 该功能才会启作用
	 * @param currentPersistent
	 * @param cache
	 */
	private void validateUniqueValue(PersistentObject currentPersistent, Map<Identity, PersistentObject> cache) {
		if(currentPersistent.existsUniqueConstraint()) {
			for (PersistentObject beforePersistent : cache.values()) {
				// 这个判断是因为update时可能会从缓存中取数据再修改, 所以防止同一个对象进行比较
				if(beforePersistent != currentPersistent) {
					doCompareUniqueValue(currentPersistent, beforePersistent.getPersistentObjectUniqueValue());
				}
			}
		}
	}
	
	/**
	 * 比较唯一值
	 * @param currentPersistent
	 * @param beforePersistentObjectValidateUniqueValue
	 */
	@SuppressWarnings("unchecked")
	private void doCompareUniqueValue(PersistentObject currentPersistent, Object beforePersistentObjectValidateUniqueValue) {
		Object currentPersistentObjectValidateUniqueValue = currentPersistent.getPersistentObjectUniqueValue();
		if((currentPersistentObjectValidateUniqueValue instanceof UniqueValue) && currentPersistentObjectValidateUniqueValue.equals(beforePersistentObjectValidateUniqueValue)) {
			UniqueConstraint uc = currentPersistent.getUniqueConstraint((byte)0);
			throw new RepeatedUniqueValueException(getColumnDescriptionNames(uc, currentPersistent), getColumnNames(uc, currentPersistent), ((UniqueValue)currentPersistentObjectValidateUniqueValue).getValue(), new UniqueValidationResult(uc.getAllCode()));
		}else if(currentPersistentObjectValidateUniqueValue instanceof List) {
			List<UniqueValue> currentPersistentObjectValidateUniqueValues = (List<UniqueValue>) currentPersistentObjectValidateUniqueValue;
			List<UniqueValue> beforePersistentObjectValidateUniqueValues = (List<UniqueValue>) beforePersistentObjectValidateUniqueValue;
			byte index=0, count = currentPersistent.getUniqueConstraintCount();
			for(; index<count; index++) {
				if(currentPersistentObjectValidateUniqueValues.get(index).equals(beforePersistentObjectValidateUniqueValues.get(index))) {
					UniqueConstraint uc = currentPersistent.getUniqueConstraint(index);
					throw new RepeatedUniqueValueException(getColumnDescriptionNames(uc, currentPersistent), getColumnNames(uc, currentPersistent), ((UniqueValue)currentPersistentObjectValidateUniqueValue).getValue(), new UniqueValidationResult(uc.getAllCode()));
				}
			}
		}
	}
	
	// 获取列的description name
	private String getColumnDescriptionNames(UniqueConstraint uc, PersistentObject currentPersistent) {
		if(uc.isMultiColumns()) {
			StringBuilder sb = new StringBuilder(uc.size()*16);
			uc.getCodes().forEach(code -> sb.append(",").append(currentPersistent.getColumnDescriptionNameByCode(code)));
			return sb.substring(1);
		}
		return currentPersistent.getColumnDescriptionNameByCode(uc.getCode());
	}
	// 获取列的name
	private String getColumnNames(UniqueConstraint uc, PersistentObject currentPersistent) {
		if(uc.isMultiColumns()) {
			StringBuilder sb = new StringBuilder(uc.size()*16);
			uc.getCodes().forEach(code -> sb.append(",").append(currentPersistent.getColumnNameByCode(code)));
			return sb.substring(1);
		}
		return currentPersistent.getColumnNameByCode(uc.getCode());
	}
	

	/**
	 * 将要【保存的持久化对象】放到缓存中
	 * @param persistent
	 */
	private void putInsertPersistentObjectCache(PersistentObject persistent) {
		if(enableTalbeSessionCache) {
			String code = persistent.getCode();
			Map<Identity, PersistentObject> cache = getCache(code);
			
			if(!cache.isEmpty()) {
				if(cache.containsKey(persistent.getId())) {
					throw new RepeatedPersistentObjectException("保存的对象["+code+"]出现重复的id值: existsObject=["+cache.get(persistent.getId())+"], thisObject=["+persistent+"]");
				}
				validateUniqueValue(persistent, cache);
			}
			cache.put(persistent.getId(), persistent);
		}else {
			executePersistentObject(persistent);
		}
	}
	
	private void save_(TableMetadata table, Object object) {
		PersistentObject persistent = new PersistentObject(table, object, OperationState.CREATE, false);
		putInsertPersistentObjectCache(persistent);
	}
	
	@Override
	public void save(Object object) {
		save_(getTableMetadata(object.getClass().getName()), object);
	}
	
	@Override
	public void save(List<Object> objects) {
		TableMetadata table = getTableMetadata(objects.get(0).getClass().getName());
		objects.forEach(object -> save_(table, object));
	}
	
	@Override
	public void save(String code, Object object) {
		save_(getTableMetadata(code), object);
	}
	
	@Override
	public void save(String code, List<Object> objects) {
		TableMetadata table = getTableMetadata(code);
		objects.forEach(propertyMap -> save_(table, propertyMap));
	}
	
	
	/**
	 * 将要【修改的持久化对象】放到缓存中
	 * @param object
	 * @param tableMetadata
	 * @param updateNullValue
	 * @param cache
	 */
	private void putUpdatePersistentObjectCache(Object object, TableMetadata tableMetadata, boolean updateNullValue, Map<Identity, PersistentObject> cache) {
		if(!tableMetadata.existsPrimaryKey()) {
			throw new UnsupportUpdatePersistentWithoutPrimaryKeyException(tableMetadata.getCode());
		}
		PersistentObject persistentObject = new PersistentObject(tableMetadata, object, OperationState.UPDATE, updateNullValue);
		if(enableTalbeSessionCache) {
			if(!cache.isEmpty() && cache.containsKey(persistentObject.getId())) {
				logger.debug("缓存中存在要修改的数据持久化对象");
				persistentObject = cache.get(persistentObject.getId());
				switch(persistentObject.getOperationState()) {
					case CREATE:
					case UPDATE:
						if(logger.isDebugEnabled()) {
							logger.debug("将{}状态的数据, 修改originObject数据后, 不对状态进行修改, 完成update", persistentObject.getOriginObject());// 如果修改create=>update, 最后发出的sql语句会不同, 试问一个没有create过的数据, 怎么可能执行成功update 
						}
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
			if(cache.size() > 1) {
				validateUniqueValue(persistentObject, cache);
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
	public void update(String code, Object object, boolean updateNullValue) {
		update_(getTableMetadata(code), object, updateNullValue);
	}
	
	@Override
	public void update(String code, List<Object> objects, boolean updateNullValue) {
		TableMetadata table = getTableMetadata(code);
		objects.forEach(propertyMap -> update_(table, propertyMap, updateNullValue));
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
	public void delete(String code, Object object) {
		delete_(getTableMetadata(code), object);
	}
	
	@Override
	public void delete(String code, List<Object> objects) {
		TableMetadata table = getTableMetadata(code);
		objects.forEach(propertyMap -> delete_(table, propertyMap));
	}

		
	private void flushPersistentObjectCache() throws SessionExecutionException {
		CollectionUtil.clear(tableMetadataCache);
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
		if(persistentObject.getOperationState() == OperationState.CREATE && persistentObject.getTableMetadata().getPrimaryKeySequence() != null) {
			// 如果是保存表数据, 且使用了序列作为主键值
			TableMetadata tableMetadata = persistentObject.getTableMetadata();
			InsertResult result = super.executeInsert(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters(), new ReturnID(tableMetadata.getPrimaryKeySequence().getName()));
			// 将执行后的序列值, 赋值给源实例
			IntrospectorUtil.setProperyValue(persistentObject.getOriginObject(), tableMetadata.getPrimaryKeyColumnCodes().iterator().next(), result.getId());
		}else {
			super.executeUpdate(executeHandler.getCurrentSql(), executeHandler.getCurrentParameters());
		}
	}
	
	@Override
	public void close() {
		if(!isClosed) {
			if(logger.isDebugEnabled()) {
				logger.debug("close {}", getClass().getName());
			}
			try {
				flushPersistentObjectCache();
			} catch(SessionExecutionException see){
				throw see;
			}finally {
				super.close();
			}
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
	 * 将map转换为类 <内部方法>
	 * @param tableMetadata
	 * @param targetClass
	 * @param resultMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T map2Class(TableMetadata tableMetadata, Class<T> targetClass, Map<String, Object> resultMap) {
		ColumnMetadata column = null;
		Set<String> codes = tableMetadata.getColumnCodes();
		for (String code : codes) {
			column = tableMetadata.getColumnByCode(code);
			resultMap.put(column.getCode(), resultMap.remove(column.getName()));
		}
		return (T) IntrospectorUtil.setProperyValues(ConstructorUtil.newInstance(targetClass), resultMap);
	}

	
	@Override
	public String getColumnNames(String code, String... excludeColumnNames) {
		TableMetadata tableMetadata = getTableMetadata(code);
		StringBuilder cns = new StringBuilder(tableMetadata.getDeclareColumns().size() * 40);
		tableMetadata.getDeclareColumns().forEach(column -> {
			if(unExcludeColumnName(column.getName(), excludeColumnNames)) {
				cns.append(", ").append(column.getName());
			}
		});
		if(cns.length() == 0) {
			throw new NullPointerException("在code=["+code+"]的表映射中, 没有匹配到任何列名, 请确保没有对过多的列名进行排除 ");
		}
		logger.debug("得到的列名为 -> {}", cns);
		return cns.substring(1);
	}
	
	// 判断指定的columnName是否没有被排除
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
