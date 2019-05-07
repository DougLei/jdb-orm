package com.douglei.sessions.session.table.impl.persistent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.sessions.session.persistent.PersistentObject;
import com.douglei.sessions.session.persistent.State;
import com.douglei.sessions.session.persistent.execution.ExecutionHolder;
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
	private State state;
	
	private TableMetadata tableMetadata;
	private Map<String, Object> propertyMap;
	
	public TablePersistentObject(TableMetadata tableMetadata, Object originObject, State state) {
		setOriginObject(originObject);
		this.tableMetadata = tableMetadata;
		setState(state);
	}
	
	@Override
	public String getCode() {
		return tableMetadata.getCode();
	}
	
	private Identity id;
	@Override
	public Identity getId() {
		if(id == null) {
			Set<String> primaryKeyColumnMetadataCodes = tableMetadata.getPrimaryKeyColumnMetadataCodes();
			Object id;
			if(primaryKeyColumnMetadataCodes.size() == 1) {
				id = propertyMap.get(primaryKeyColumnMetadataCodes.iterator().next());
			}else {
				Map<String, Object> idMap = new HashMap<String, Object>(primaryKeyColumnMetadataCodes.size());
				for (String pkCode : primaryKeyColumnMetadataCodes) {
					idMap.put(pkCode, propertyMap.get(pkCode));
				}
				id = idMap;
			}
			this.id = new Identity(id);
		}
		return id;
	}
	
	@Override
	public void setIdentity(Identity identity) {
		this.id = identity;
	}
	
	@Override
	public String toString() {
		return "\n"
				+ "state:" + state 
				+ "\n" 
				+ originObject.toString();
	}
	
	@Override
	public ExecutionHolder getExecutionHolder() {
		switch(state) {
			case CREATE:
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
	public Object getOriginObject() {
		return originObject;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setOriginObject(Object originObject) {
		if(originObject instanceof Map) {
			logger.debug("originObject is Map type, 从该map中, 筛选出相关列的数据信息");
			propertyMap = filterColumnMetadatasPropertyMap(tableMetadata, (Map<String, Object>)originObject);
		}else {
			logger.debug("originObject is Object type [{}], 从该object中, 通过java内省机制, 获取相关列的数据信息", originObject.getClass());
			propertyMap = IntrospectorUtil.getProperyValues(originObject, tableMetadata.getColumnMetadataCodes());
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
	
	/**
	 * 从originPropertyMap集合中, 筛选出相关列的数据信息
	 * @param tableMetadata
	 * @param originPropertyMap 
	 * @return
	 */
	private Map<String, Object> filterColumnMetadatasPropertyMap(TableMetadata tableMetadata, Map<String, Object> originPropertyMap) {
		Set<String> columnMetadataCodes = tableMetadata.getColumnMetadataCodes();
		int columnSize = columnMetadataCodes.size();
		Map<String, Object> resultPropertyMap = new HashMap<String, Object>(columnSize);
		
		int index = 1;
		Set<String> originPropertyMapKeys = originPropertyMap.keySet();
		for (String originPMkey : originPropertyMapKeys) {
			if(tableMetadata.isColumn(originPMkey)) {
				resultPropertyMap.put(tableMetadata.getColumnMetadata(originPMkey).getCode(), originPropertyMap.get(originPMkey));
				if(index == columnSize) {
					break;
				}
				index++;
			}
		}
		return resultPropertyMap;
	}
}
