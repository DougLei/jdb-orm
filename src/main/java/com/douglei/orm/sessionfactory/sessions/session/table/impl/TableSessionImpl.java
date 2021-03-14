package com.douglei.orm.sessionfactory.sessions.session.table.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.ConnectionEntity;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sessionfactory.sessions.SessionExecuteException;
import com.douglei.orm.sessionfactory.sessions.session.table.TableSession;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.Operation;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.PersistentObject;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql.AbstractExecutableTableSql;
import com.douglei.orm.sessionfactory.sessions.sqlsession.SqlSessionImpl;
import com.douglei.orm.sql.statement.AutoIncrementID;
import com.douglei.orm.sql.statement.InsertResult;
import com.douglei.tools.reflect.ClassUtil;
import com.douglei.tools.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class TableSessionImpl extends SqlSessionImpl implements TableSession {
	private static final Logger logger = LoggerFactory.getLogger(TableSessionImpl.class);
	private Map<String, TableMetadata> cache = new HashMap<String, TableMetadata>(8);
	
	public TableSessionImpl(ConnectionEntity connection, Environment environment) {
		super(connection, environment);
	}
	
	/**
	 * 获取table元数据实例
	 * @param code
	 * @return
	 */
	private TableMetadata getTableMetadata(String code) {
		TableMetadata tableMetadata = cache.get(code);
		if(tableMetadata == null) {
			tableMetadata = mappingHandler.getTableMetadata(code);
			cache.put(code, tableMetadata);
		}
		return tableMetadata;
	}
	
	// -----------------------------------------------------------------------------------------------
	// 保存
	// -----------------------------------------------------------------------------------------------
	/**
	 * 保存对象
	 * @param tableMetadata
	 * @param object
	 */
	private void save_(TableMetadata tableMetadata, Object object) {
		executePersistentObject(new PersistentObject(tableMetadata, object, Operation.INSERT));
	}
	
	@Override
	public void save(Object object) {
		save_(getTableMetadata(object.getClass().getName()), object);
	}
	
	@Override
	public void save(List<? extends Object> objects) {
		TableMetadata tableMetadata = getTableMetadata(objects.get(0).getClass().getName());
		objects.forEach(object -> save_(tableMetadata, object));
	}
	
	@Override
	public void save(String tableName, Map<String, Object> objectMap) {
		save_(getTableMetadata(tableName.toUpperCase()), objectMap);
	}
	
	@Override
	public void save(String tableName, List<Map<String, Object>> objectMaps) {
		TableMetadata tableMetadata = getTableMetadata(tableName.toUpperCase());
		objectMaps.forEach(objectMap -> save_(tableMetadata, objectMap));
	}
	
	// -----------------------------------------------------------------------------------------------
	// 修改
	// -----------------------------------------------------------------------------------------------
	/**
	 * 修改对象
	 * @param tableMetadata
	 * @param object
	 * @param updateNullValue
	 */
	private void update_(TableMetadata tableMetadata, Object object, boolean updateNullValue) {
		if(tableMetadata.getPrimaryKeyConstraint() == null) 
			throw new SessionExecuteException("不支持update没有主键的表数据 ["+tableMetadata.getCode()+"]"); // 因为没法区分数据中哪些应该被update set, 哪些应该做where条件
		
		PersistentObject persistent = new PersistentObject(tableMetadata, object, Operation.UPDATE);
		persistent.setUpdateNullValue(updateNullValue);
		executePersistentObject(persistent);
	}
	
	@Override
	public void update(Object object, boolean updateNullValue) {
		update_(getTableMetadata(object.getClass().getName()), object, updateNullValue);
	}
	
	@Override
	public void update(List<? extends Object> objects, boolean updateNullValue) {
		TableMetadata tableMetadata = getTableMetadata(objects.get(0).getClass().getName());
		objects.forEach(object -> update_(tableMetadata, object, updateNullValue));
	}
	
	@Override
	public void update(String tableName, Map<String, Object> objectMap, boolean updateNullValue) {
		update_(getTableMetadata(tableName.toUpperCase()), objectMap, updateNullValue);
	}
	
	@Override
	public void update(String tableName, List<Map<String, Object>> objectMaps, boolean updateNullValue) {
		TableMetadata tableMetadata = getTableMetadata(tableName.toUpperCase());
		objectMaps.forEach(objectMap -> update_(tableMetadata, objectMap, updateNullValue));
	}
	
	// -----------------------------------------------------------------------------------------------
	// 删除
	// -----------------------------------------------------------------------------------------------
	/**
	 * 删除对象
	 * @param tableMetadata
	 * @param object
	 */
	private void delete_(TableMetadata tableMetadata, Object object) {
		executePersistentObject(new PersistentObject(tableMetadata, object, Operation.DELETE));
	}

	@Override
	public void delete(Object object) {
		delete_(getTableMetadata(object.getClass().getName()), object);
	}
	
	@Override
	public void delete(List<? extends Object> objects) {
		TableMetadata tableMetadata = getTableMetadata(objects.get(0).getClass().getName());
		objects.forEach(object -> delete_(tableMetadata, object));
	}
	
	@Override
	public void delete(String tableName, Map<String, Object> objectMap) {
		delete_(getTableMetadata(tableName.toUpperCase()), objectMap);
	}
	
	@Override
	public void delete(String tableName, List<Map<String, Object>> objectMaps) {
		TableMetadata tableMetadata = getTableMetadata(tableName.toUpperCase());
		objectMaps.forEach(objectMap -> delete_(tableMetadata, objectMap));
	}

	// -----------------------------------------------------------------------------------------------
	// 执行
	// -----------------------------------------------------------------------------------------------
	private void executePersistentObject(PersistentObject persistentObject) throws SessionExecuteException {
		TableMetadata tableMetadata = persistentObject.getTableMetadata();
		
		AbstractExecutableTableSql executableTableSql = persistentObject.getExecutableTableSql();
		if(persistentObject.getOperation() == Operation.INSERT && tableMetadata.getAutoincrementPrimaryKey() != null) {
			// 如果是保存表数据, 且使用了序列作为主键值
			InsertResult result = super.executeInsert(executableTableSql.getCurrentSql(), executableTableSql.getCurrentParameterValues(), new AutoIncrementID(tableMetadata.getAutoincrementPrimaryKey().getSequence()));
			// 将执行insert语句后生成的序列值, 赋给源实例
			String code = tableMetadata.getColumnMap4Name().get(tableMetadata.getAutoincrementPrimaryKey().getColumn()).getCode();
			IntrospectorUtil.setValue(code, result.getAutoIncrementIDValue(), persistentObject.getOriginObject());
		}else {
			super.executeUpdate(executableTableSql.getCurrentSql(), executableTableSql.getCurrentParameterValues());
		}
	}

	// -----------------------------------------------------------------------------------------------
	// 查询
	// -----------------------------------------------------------------------------------------------
	@Override
	protected <T> List<T> listMap2listClass(Class<T> clazz, List<Map<String, Object>> resultListMap) {
		TableMetadata tableMetadata = getTableMetadata(clazz.getName());
		List<T> targetList = new ArrayList<T>(resultListMap.size());
		for (Map<String, Object> resultMap : resultListMap) 
			targetList.add(map2Class(tableMetadata, clazz, resultMap));
		return targetList;
	}

	@Override
	protected <T> T map2Class(Class<T> clazz, Map<String, Object> resultMap) {
		return map2Class(getTableMetadata(clazz.getName()), clazz, resultMap);
	}
	
	/**
	 * 将map转换为类
	 * @param tableMetadata
	 * @param clazz
	 * @param resultMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T map2Class(TableMetadata tableMetadata, Class<T> clazz, Map<String, Object> resultMap) {
		tableMetadata.getColumns().forEach(column -> {
			resultMap.put(column.getProperty(), resultMap.remove(column.getName()));
		});
		
		Object object = ClassUtil.newInstance(clazz);
		IntrospectorUtil.setValues(resultMap, object);
		return (T) object;
	}

	// -----------------------------------------------------------------------------------------------
	// 关闭
	// -----------------------------------------------------------------------------------------------
	@Override
	public void close() {
		if(!isClosed) {
			if(logger.isDebugEnabled()) 
				logger.debug("close {}", getClass().getName());
			
			cache.clear();
			super.close();
		}
	}
}
