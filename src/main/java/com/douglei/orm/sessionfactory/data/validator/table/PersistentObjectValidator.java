package com.douglei.orm.sessionfactory.data.validator.table;

import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.AbstractPersistentObject;

/**
 * 持久化对象验证器
 * @author DougLei
 */
public class PersistentObjectValidator extends AbstractPersistentObject{
	
	public PersistentObjectValidator(TableMetadata tableMetadata) {
		super(tableMetadata);
	}
	public PersistentObjectValidator(TableMetadata tableMetadata, Object originObject) {
		super(tableMetadata, originObject);
	}


	// 进行验证
	public ValidationResult doValidate() {
		if(tableMetadata.existsValidateColumns()) {
			Object value = null;
			ValidationResult result = null;
			for(ColumnMetadata column : tableMetadata.getValidateColumns()) {
				value = propertyMap.get(column.getCode());
				result = column.getValidatorHandler().doValidate(value);
				if(result != null) {
					return result;
				}
			}
		}
		return null;
	}
}
