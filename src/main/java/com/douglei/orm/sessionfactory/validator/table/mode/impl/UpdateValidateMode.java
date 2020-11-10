package com.douglei.orm.sessionfactory.validator.table.mode.impl;

import java.util.Map;
import java.util.Map.Entry;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.validator.table.mode.Mode;
import com.douglei.orm.sessionfactory.validator.table.mode.ValidateMode;

/**
 * 
 * @author DougLei
 */
public class UpdateValidateMode implements ValidateMode {
	public static final UpdateValidateMode DEFAULT_INSTANCE = new UpdateValidateMode(false);
	private boolean updateNullValue;
	
	/**
	 * 
	 * @param updateNullValue 是否更新null值, 如果要更新null值, 则会对必要的null值进行验证
	 */
	public UpdateValidateMode(boolean updateNullValue) {
		this.updateNullValue = updateNullValue;
	}

	@Override
	public ValidationResult validate(Map<String, Object> objectMap, TableMetadata table) {
		ValidationResult result = null;
		
		// 验证update set的参数
		ColumnMetadata column = null;
		for (Entry<String, Object> entry : objectMap.entrySet()) {
			column = table.getColumns_().get(entry.getKey());
			if(column.getValidateHandler() == null)
				continue;

			if(column.isPrimaryKey()) {
				if((result = column.getValidateHandler().validate(entry.getValue())) != null)
					return result;
				continue;
			}
			
			if(!updateNullValue && entry.getValue() == null)
				continue;
			if((result = column.getValidateHandler().validate(entry.getValue())) != null)
				return result;
		}
		return null;
	}

	@Override
	public Mode getMode() {
		return Mode.UPDATE;
	}
}
