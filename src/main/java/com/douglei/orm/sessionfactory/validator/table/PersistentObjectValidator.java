package com.douglei.orm.sessionfactory.validator.table;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.AbstractPersistentObject;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.Operation;
import com.douglei.orm.sessionfactory.validator.DataValidateException;
import com.douglei.orm.sessionfactory.validator.table.handler.ValidateHandler;

/**
 * 持久化对象验证器
 * @author DougLei
 */
public class PersistentObjectValidator extends AbstractPersistentObject {
	private boolean supportValidate; // 是否支持验证
	private ValidateHandler validateHandler; // 验证处理器
	
	public PersistentObjectValidator(TableMetadata tableMetadata, ValidateHandler validateHandler) {
		super(tableMetadata);
		
		if(validateHandler.getOperation() == Operation.UPDATE && tableMetadata.getPrimaryKeyConstraint() == null) 
			throw new DataValidateException("update验证模式下, 不支持验证没有主键的表数据 ["+tableMetadata.getCode()+"]"); // 因为没法区分数据中哪些应该被update set, 哪些应该做where条件
		
		for (ColumnMetadata column : tableMetadata.getColumns()) {
			if(column.getValidators() != null) {
				this.supportValidate = true;
				this.validateHandler = validateHandler;
				break;	
			}
		}
	}

	/**
	 * 进行验证
	 * @param originObject
	 * @return
	 */
	public ValidateFailResult validate(Object originObject) {
		if(supportValidate) {
			setOriginObject(originObject);
			
			ValidateFailResult failResult = validateHandler.validate(objectMap, tableMetadata);
			if(failResult != null)
				return failResult;
		}
		return null;
	}
}
