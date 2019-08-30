package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
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
	private Object validateUniqueValue;// 要验证的唯一约束的值
	
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
	
	/**
	 * 获取验证的, 有唯一约束的列code集合
	 * @return
	 */
	public List<String> getValidateUniqueColumnCodes() {
		return validateUniqueColumnCodes;
	}
	
	@Override
	public boolean existsValidateUniqueColumns() {
		return EnvironmentContext.getEnvironmentProperty().enableDataValidate() && super.existsValidateUniqueColumns();
	}
	
	/**
	 * 根据code, 获取要验证唯一约束的列对象
	 * @param code
	 * @return
	 */
	public ColumnMetadata getValidateUniqueColumnByCode(String code) {
		return tableMetadata.getColumnByCode(code);
	}

	@Override
	public Object getPersistentObjectValidateUniqueValue() {
		if(validateUniqueValue == null) {
			validateUniqueValue = super.getPersistentObjectValidateUniqueValue();
		}
		return validateUniqueValue;
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
				this.id = new Identity(id, tableMetadata);
			}else {
				this.id = new Identity(propertyMap);// 不存在主键配置时, 就将整个对象做为id
			}
		}
		return id;
	}
	
	// 获取执行器实例
	public ExecuteHandler getExecuteHandler() {
		switch(operationState) {
			case CREATE:
				return new InsertExecuteHandler(tableMetadata, propertyMap);
			case DELETE:
				return new DeleteExecuteHandler(tableMetadata, propertyMap);
			case UPDATE:
				return new UpdateExecuteHandler(tableMetadata, propertyMap);
		}
		return null;
	}
	
	@Override
	public void setOriginObject(Object originObject) {
		super.setOriginObject(originObject);
		doValidate();
		this.validateUniqueValue = null;// 将唯一值置空
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
					throw new DataValidationException(column.getDescriptionName(), column.getName(), value, result);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return "id:" + getId().toString() 
				+ "\t" 
				+ "operationState:" + operationState 
				+ "\t" 
				+ originObject.toString();
	}
}
