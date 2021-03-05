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
public class DeleteValidateHandler implements ValidateHandler {
	private DeleteValidateHandler() {}
	private static final DeleteValidateHandler singleton = new DeleteValidateHandler();
	public static DeleteValidateHandler getSingleton() {
		return singleton;
	}
	
	@Override
	public ValidateFailResult validate(Map<String, Object> objectMap, TableMetadata tableMetadata) {
		// 没有主键时, 验证所有配置了验证器的列数据
		if(tableMetadata.getPrimaryKeyConstraint() == null) 
			return InsertValidateHandler.getSingleton().validate(objectMap, tableMetadata);
		
		// 有主键时, 验证配置了验证器的主键列数据
		ValidatedData data = new ValidatedData();
		for(String columnName: tableMetadata.getPrimaryKeyConstraint().getColumnNameList()) {
			ColumnMetadata column = tableMetadata.getColumnMap4Name().get(columnName);
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
		return Operation.DELETE;
	}
}
