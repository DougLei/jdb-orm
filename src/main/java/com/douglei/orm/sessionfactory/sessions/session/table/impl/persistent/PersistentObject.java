package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.table.UniqueConstraint;
import com.douglei.orm.core.metadata.validator.DataValidationException;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.execute.ExecuteHandler;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execute.DeleteExecuteHandler;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execute.InsertExecuteHandler;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.execute.UpdateExecuteHandler;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.id.Identity;

/**
 * 
 * @author DougLei
 */
public class PersistentObject extends AbstractPersistentObject{
	private Identity id;
	private OperationState operationState;
	private boolean updateNullValue; // 修改时使用
	private Object uniqueValue;// 唯一约束的值
	
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

	@Override
	public boolean existsUniqueConstraint() {
		return EnvironmentContext.getEnvironmentProperty().enableDataValidate() && super.existsUniqueConstraint();
	}
	
	/**
	 * 获取唯一约束的数量
	 * @return
	 */
	public byte getUniqueConstraintCount() {
		if(existsUniqueConstraint()) {
			return (byte) uniqueConstraints.size();
		}
		return -1;
	}
	
	/**
	 * 获取指定下标的唯一约束
	 * @param index
	 * @return
	 */
	public UniqueConstraint getUniqueConstraint(byte index) {
		if(existsUniqueConstraint()) {
			return uniqueConstraints.get(index);
		}
		return null;
	}
	
	@Override
	public Object getPersistentObjectUniqueValue() {
		if(uniqueValue == null) {
			uniqueValue = super.getPersistentObjectUniqueValue();
		}
		return uniqueValue;
	}
	
	public String getColumnNameByCode(String code) {
		return tableMetadata.getColumnByCode(code).getName();
	}
	public String getColumnDescriptionNameByCode(String code) {
		return tableMetadata.getColumnByCode(code).getDescriptionName();
	}
	

	public Identity getId() {
		if(id == null) {
			if(tableMetadata.existsPrimaryKey()) {
				Set<String> primaryKeyColumnMetadataCodes = tableMetadata.getPrimaryKeyColumnCodes();
				Object id;
				if(primaryKeyColumnMetadataCodes.size() == 1) {
					id = objectMap.get(primaryKeyColumnMetadataCodes.iterator().next());
				}else {
					Map<String, Object> idMap = new HashMap<String, Object>(primaryKeyColumnMetadataCodes.size());
					for (String pkCode : primaryKeyColumnMetadataCodes) {
						idMap.put(pkCode, objectMap.get(pkCode));
					}
					id = idMap;
				}
				this.id = new Identity(id, tableMetadata);
			}else {
				this.id = new Identity(objectMap);// 不存在主键配置时, 就将整个对象做为id
			}
		}
		return id;
	}
	
	// 获取执行器实例
	public ExecuteHandler getExecuteHandler() {
		switch(operationState) {
			case CREATE:
				return new InsertExecuteHandler(tableMetadata, objectMap, originObject);
			case DELETE:
				return new DeleteExecuteHandler(tableMetadata, objectMap);
			case UPDATE:
				return new UpdateExecuteHandler(tableMetadata, objectMap, updateNullValue);
		}
		return null;
	}
	
	@Override
	public void setOriginObject(Object originObject) {
		super.setOriginObject(originObject);
		doValidate();
		this.uniqueValue = null;// 将唯一值置空
	}

	// 进行验证
	private void doValidate() {
		if(EnvironmentContext.getEnvironmentProperty().enableDataValidate() && tableMetadata.existsValidateColumns()) {
			Object value = null;
			ValidationResult result = null;
			for(ColumnMetadata column : tableMetadata.getValidateColumns()) {
				value = objectMap.get(column.getCode());
				result = column.getValidateHandler().validate(value);
				if(result != null) {
					throw new DataValidationException(column.getDescriptionName(), column.getName(), value, result);
				}
			}
		}
	}

	@Override
	public String toString() {
		return "PersistentObject [id=" + id + ", operationState=" + operationState + ", originObject=" + originObject + "]";
	}
}
