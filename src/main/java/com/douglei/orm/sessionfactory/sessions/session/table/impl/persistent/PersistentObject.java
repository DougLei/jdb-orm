package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.validator.DataValidateException;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.execution.ExecutionHolder;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execution.DeleteExecutionHolder;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execution.InsertExecutionHolder;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execution.UpdateExecutionHolder;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.id.Identity;

/**
 * 
 * @author DougLei
 */
public class PersistentObject extends AbstractPersistentObject{
	private Identity id;
	private OperationState operationState;
	
	public PersistentObject(TableMetadata tableMetadata, Object originObject, OperationState operationState) {
		super(tableMetadata, originObject);
		setOperationState(operationState);
	}
	
	public OperationState getOperationState() {
		return operationState;
	}
	public void setOperationState(OperationState operationState) {
		this.operationState = operationState;
	}
	public String getCode() {
		return tableMetadata.getCode();
	}
	public Identity getId() {
		if(id == null) {
			if(tableMetadata.existsPrimaryKey()) {
				Set<String> primaryKeyColumnMetadataCodes = tableMetadata.getPrimaryKeyColumnCodes();
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
				this.id.setTableMetadata(tableMetadata);
			}else {
				this.id = new Identity(propertyMap);// 不存在主键配置时, 就将整个对象做为id
			}
		}
		return id;
	}
	public void setId(Identity identity) {
		this.id = identity;
	}
	
	// 获取执行器实例
	public ExecutionHolder getExecutionHolder() {
		switch(operationState) {
			case CREATE:
				return new InsertExecutionHolder(tableMetadata, propertyMap);
			case DELETE:
				return new DeleteExecutionHolder(tableMetadata, propertyMap);
			case UPDATE:
				return new UpdateExecutionHolder(tableMetadata, propertyMap);
		}
		return null;
	}
	
	@Override
	public void setOriginObject(Object originObject) {
		super.setOriginObject(originObject);
		doValidate();
	}

	// 进行验证
	private void doValidate() {
		if(EnvironmentContext.getEnvironmentProperty().enableDataValidate() && tableMetadata.existsValidateColumns()) {
			Object value = null;
			ValidationResult result = null;
			for(ColumnMetadata column : tableMetadata.getValidateColumns()) {
				value = propertyMap.get(column.getCode());
				result = column.getValidatorHandler().doValidate(value);
				if(result != null) {
					throw new DataValidateException(column.getDescriptionName(), column.getName(), value, result);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return "operationState:" + operationState 
				+ "\t" 
				+ originObject.toString();
	}
}
