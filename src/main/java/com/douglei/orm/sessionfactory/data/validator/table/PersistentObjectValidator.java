package com.douglei.orm.sessionfactory.data.validator.table;

import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.AbstractPersistentObject;

/**
 * 持久化对象验证器
 * @author DougLei
 */
public class PersistentObjectValidator extends AbstractPersistentObject {
	
	public PersistentObjectValidator(TableMetadata tableMetadata) {
		super(tableMetadata);
	}

	// 进行验证
	public ValidationResult doValidate(Object originObject) {
		if(tableMetadata.existsValidateColumns()) {
			setOriginObject(originObject);
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
	// TODO 完成唯一约束的验证
	public boolean existsValidateUniqueColumns() {
		return tableMetadata.existsValidateUniqueColumns();
	}
}
