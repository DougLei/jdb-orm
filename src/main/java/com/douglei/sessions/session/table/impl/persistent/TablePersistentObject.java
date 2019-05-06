package com.douglei.sessions.session.table.impl.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.sessions.session.persistent.PersistentObject;
import com.douglei.sessions.session.persistent.State;
import com.douglei.sessions.session.persistent.execution.ExecutionHolder;
import com.douglei.sessions.session.persistent.execution.ExecutionType;
import com.douglei.sessions.session.persistent.id.Identity;
import com.douglei.sessions.session.table.impl.persistent.execution.DeleteExecutionHolder;
import com.douglei.sessions.session.table.impl.persistent.execution.InsertExecutionHolder;
import com.douglei.sessions.session.table.impl.persistent.execution.UpdateExecutionHolder;
import com.douglei.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class TablePersistentObject implements PersistentObject{
	private static final Logger logger = LoggerFactory.getLogger(TablePersistentObject.class);
	
	private Object originObject;
	private Object classObject;
	private State state;
	
	private TableMetadata tableMetadata;
	private Map<String, Object> propertyMap;
	
	public TablePersistentObject(TableMetadata tableMetadata, Object originObject) {
		setOriginObject(originObject);
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
		
		ColumnMetadata columnMetadata = null;
		Set<String> propertyMapKeys = propertyMap.keySet();
		for (String pmkey : propertyMapKeys) {
			columnMetadata = tableMetadata.getColumnMetadata(pmkey);
			if(columnMetadata != null) {
				resultPropertyMap.put(columnMetadata.getCode(), propertyMap.get(pmkey));
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
			if(logger.isDebugEnabled()) {
				logger.debug("获取持久化对象id为: {}", id.toString());
			}
			this.id = new Identity(id);
		}
		return id;
	}
	
	@Override
	public String toString() {
		return originObject.toString();
	}
	
	@Override
	public ExecutionHolder getExecutionHolder(ExecutionType executionType) {
		switch(executionType) {
			case INSERT:
				return new InsertExecutionHolder(tableMetadata, propertyMap);
			case DELETE:
				return new DeleteExecutionHolder(tableMetadata, propertyMap);
			case UPDATE:
				return new UpdateExecutionHolder(tableMetadata, propertyMap);
			default:
				return null;
		}
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
	public Object getClassObject() {
		return classObject;
	}
	
	@Override
	public void setClassObject(Object classObject) {
		this.classObject = classObject;
	}
	
	@Override
	public Object getOriginObject() {
		return originObject;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setOriginObject(Object originObject) {
		if(originObject instanceof Map) {
			logger.debug("propertyObject is Map type, 从该map中, 筛选出相关列的数据信息");
			propertyMap = filterColumnMetadatasPropertyMap(tableMetadata, (Map<String, Object>)originObject);
		}else {
			logger.debug("propertyObject is Object type, 从该object中, 通过java内省机制, 获取相关列的数据信息");
			propertyMap = IntrospectorUtil.getProperyValues(originObject, tableMetadata.getColumnMetadataCodes());
			setClassObject(originObject);
		}
		if(propertyMap == null || propertyMap.size() == 0) {
			logger.debug("最终propertyMap为空");
			throw new NullPointerException("要操作的数据不能为空");
		}
		if(logger.isDebugEnabled()) {
			logger.debug("获取的最终propertyMap为: {}", propertyMap.toString());
		}
		this.originObject = originObject;
	}
}
