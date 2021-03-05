package com.douglei.orm.sessionfactory.validator.table.handler;

import java.util.Map;
import java.util.Map.Entry;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.mapping.validator.ValidatedData;
import com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent.Operation;

/**
 * 
 * @author DougLei
 */
public class UpdateValidateHandler implements ValidateHandler {
	private boolean updateNullValue; // 是否更新null值
	private UpdateValidateHandler(boolean updateNullValue) {
		this.updateNullValue = updateNullValue;
	}
	
	private static final UpdateValidateHandler instance4NotUpdateNullValue = new UpdateValidateHandler(false);
	private static final UpdateValidateHandler instance4UpdateNullValue = new UpdateValidateHandler(true);
	public static UpdateValidateHandler getInstance4NotUpdateNullValue() {
		return instance4NotUpdateNullValue;
	}
	public static UpdateValidateHandler getInstance4UpdateNullValue() {
		return instance4UpdateNullValue;
	}
	
	@Override
	public ValidateFailResult validate(Map<String, Object> objectMap, TableMetadata tableMetadata) {
		ValidatedData data = new ValidatedData();
		for (Entry<String, Object> entry : objectMap.entrySet()) {
			ColumnMetadata column = tableMetadata.getColumnMap4Name().get(entry.getKey());
			if(column.getValidators() == null)
				continue;

			// 三种情况, 必须进行验证: 1. 当前列是主键; 2. 当前值不为null; 3. 当前值为null, 且updateNullValue=true; 反之则不需要验证
			if(!tableMetadata.getPrimaryKeyConstraint().getColumnNameList().contains(column.getName()) && entry.getValue() == null && !updateNullValue)
				continue;
			
			data.setValue(entry.getValue(), column);
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
		return Operation.UPDATE;
	}
}
