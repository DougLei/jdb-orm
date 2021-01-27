package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql.ExecutableDeleteSql;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql.ExecutableInsertSql;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql.ExecutableTableSql;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.sql.ExecutableUpdateSql;

/**
 * 
 * @author DougLei
 */
public class PersistentObject extends AbstractPersistentObject{
	private Identity id;
	private OperationState operationState;
	private boolean updateNullValue; // 修改时使用
	
	public PersistentObject(TableMetadata tableMetadata, Object originObject, OperationState operationState, boolean updateNullValue) {
		super(tableMetadata, originObject);
		setOperationState(operationState);
		setUpdateNullValue(updateNullValue);
	}
	
	public OperationState getOperationState() {
		return operationState;
	}
	public void setOperationState(OperationState operationState) {
		this.operationState = operationState;
	}
	public void setUpdateNullValue(boolean updateNullValue) {
		if(operationState == OperationState.UPDATE)
			this.updateNullValue = updateNullValue;
	}
	
	public Identity getId() {
		if(id == null) {
			if(tableMetadata.getPrimaryKeyColumns_() == null) {
				this.id = new Identity(objectMap);// 不存在主键配置时, 就将整个对象做为id
			}else {
				Set<String> primaryKeyColumnMetadataCodes = tableMetadata.getPrimaryKeyColumns_().keySet();
				Object id;
				if(primaryKeyColumnMetadataCodes.size() == 1) {
					id = objectMap.get(primaryKeyColumnMetadataCodes.iterator().next());
				}else {
					Map<String, Object> idMap = new HashMap<String, Object>(primaryKeyColumnMetadataCodes.size());
					for (String pkCode : primaryKeyColumnMetadataCodes) 
						idMap.put(pkCode, objectMap.get(pkCode));
					id = idMap;
				}
				this.id = new Identity(id, tableMetadata);
			}
		}
		return id;
	}
	
	// 获取可执行的sql
	public ExecutableTableSql getExecutableTableSql() {
		switch(operationState) {
			case INSERT:
				return new ExecutableInsertSql(tableMetadata, objectMap, originObject);
			case DELETE:
				return new ExecutableDeleteSql(tableMetadata, objectMap);
			case UPDATE:
				return new ExecutableUpdateSql(tableMetadata, objectMap, updateNullValue);
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "PersistentObject [id=" + id + ", operationState=" + operationState + ", originObject=" + originObject + "]";
	}
}
