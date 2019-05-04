package com.douglei.sessions.session.persistent.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.sessions.session.persistent.ExecutionHolder;
import com.douglei.sessions.session.persistent.ExecutionType;
import com.douglei.sessions.session.persistent.Identity;
import com.douglei.sessions.session.persistent.PersistentObject;
import com.douglei.sessions.session.persistent.State;
import com.douglei.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class TablePersistentObject implements PersistentObject{
	private static final Logger logger = LoggerFactory.getLogger(TablePersistentObject.class);
	
	private TableMetadata tableMetadata;
	private Map<String, Object> propertyMap;
	private State state;
	
	@SuppressWarnings("unchecked")
	public TablePersistentObject(TableMetadata tableMetadata, Object propertyObject) {
		if(propertyObject instanceof Map) {
			logger.debug("propertyObject is Map type, 从该map中, 筛选出相关列的数据信息");
			propertyMap = filterColumnMetadatasPropertyMap(tableMetadata, (Map<String, Object>)propertyObject);
		}else {
			logger.debug("propertyObject is Object type, 从该object中, 通过java内省机制, 获取相关列的数据信息");
			propertyMap = IntrospectorUtil.getProperyValues(propertyObject, tableMetadata.getColumnMetadataCodes());
		}
		if(logger.isDebugEnabled()) {
			logger.debug("获取的最终propertyMap为: {}", propertyMap.toString());
		}
		if(propertyMap == null || propertyMap.size() == 0) {
			logger.debug("最终propertyMap为空");
			throw new NullPointerException("要操作的数据不能为空");
		}
		this.tableMetadata = tableMetadata;
	}
	/**
	 * 从propertyMap集合中, 筛选出相关列的数据信息
	 * @param tableMetadata
	 * @param propertyMap 
	 * @return
	 */
	private Map<String, Object> filterColumnMetadatasPropertyMap(TableMetadata tableMetadata, Map<String, Object> propertyMap) {
		Set<String> columnMetadataCodes = tableMetadata.getColumnMetadataCodes();
		Map<String, Object> resultPropertyMap = new HashMap<String, Object>(columnMetadataCodes.size());
		
		Object value = null;
		ColumnMetadata columnMetadata = null;
		Set<String> propertyMapKeys = propertyMap.keySet();
		for (String pmkey : propertyMapKeys) {
			value = propertyMap.get(pmkey);
			if(value != null) {
				columnMetadata = tableMetadata.getColumnMetadata(pmkey);
				if(columnMetadata != null) {
					resultPropertyMap.put(columnMetadata.getCode(), value);
				}
			}
		}
		return resultPropertyMap;
	}
	
	@Override
	public String getCode() {
		return tableMetadata.getCode();
	}
	
	private Identity id;
	@Override
	public Identity getId() {
		if(id == null) {
			List<ColumnMetadata> primaryKeyColumns = tableMetadata.getPrimaryKeyColumns();
			Object id;
			if(primaryKeyColumns.size() == 1) {
				id = propertyMap.get(primaryKeyColumns.get(0).getCode());
			}else {
				Map<String, Object> idMap = new HashMap<String, Object>(primaryKeyColumns.size());
				for (ColumnMetadata cm : primaryKeyColumns) {
					idMap.put(cm.getCode(), propertyMap.get(cm.getCode()));
				}
				id = idMap;
			}
			logger.debug("获取持久化对象id为: {}", id);
			this.id = new Identity(id);
		}
		return id;
	}
	
	@Override
	public String toString() {
		return propertyMap.toString();
	}
	@Override
	public State getState() {
		return state;
	}
	@Override
	public void setState(State state) {
		this.state = state;
	}
	
	@Override
	public ExecutionHolder getExecutionHolder(ExecutionType executionType) {
		switch(executionType) {
			case INSERT:
				// TODO
				return null;
			case DELETE:
				// TODO
				return null;
			case UPDATE:
				// TODO
				return null;
		}
		return null;
	}
}
