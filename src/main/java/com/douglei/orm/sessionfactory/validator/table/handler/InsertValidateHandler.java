package com.douglei.orm.sessionfactory.validator.table.handler;

import java.util.Map;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.mapping.validator.ValidatedData;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.Operation;

/**
 * 
 * @author DougLei
 */
public class InsertValidateHandler implements ValidateHandler {
	private InsertValidateHandler() {}
	private static final InsertValidateHandler singleton = new InsertValidateHandler();
	public static InsertValidateHandler getSingleton() {
		return singleton;
	}
	
	@Override
	public ValidateFailResult validate(Map<String, Object> objectMap, TableMetadata tableMetadata) {
		ValidatedData data = new ValidatedData();
		for(ColumnMetadata column: tableMetadata.getColumns()) {
			if(column.getValidators() == null)
				continue;
			
			data.setValue(objectMap.get(column.getCode()), column);
			for(Validator validator: column.getValidators()) {
				ValidateFailResult failResult = validator.validate(data);
				if(failResult != null)
					return failResult;
			}
		}
		return null;
	}

	@Override
	public Operation getOperation() {
		return Operation.INSERT;
	}
}
