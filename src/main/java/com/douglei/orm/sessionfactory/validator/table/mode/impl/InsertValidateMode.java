package com.douglei.orm.sessionfactory.validator.table.mode.impl;

import java.util.Map;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.validator.ValidationResult;
import com.douglei.orm.sessionfactory.validator.table.mode.Mode;
import com.douglei.orm.sessionfactory.validator.table.mode.ValidateMode;

/**
 * 
 * @author DougLei
 */
public class InsertValidateMode implements ValidateMode {
	private static final InsertValidateMode singleton = new InsertValidateMode();
	public static InsertValidateMode getSingleton() {
		return singleton;
	}
	
	private InsertValidateMode() {}

	@Override
	public ValidationResult validate(Map<String, Object> objectMap, TableMetadata table) {
		ValidationResult result = null;
		for(ColumnMetadata column : table.getValidateColumns()) {
			if((result = column.getValidateHandler().validate(objectMap.get(column.getCode()))) != null) 
				return result;
		}
		return null;
	}

	@Override
	public Mode getMode() {
		return Mode.INSERT;
	}
}
