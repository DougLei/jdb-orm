package com.douglei.orm.sessionfactory.validator.table.mode.impl;

import java.util.Map;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.validator.table.mode.Mode;
import com.douglei.orm.sessionfactory.validator.table.mode.ValidateMode;

/**
 * 
 * @author DougLei
 */
public class DeleteValidateMode implements ValidateMode {
	private static final DeleteValidateMode singleton = new DeleteValidateMode();
	public static DeleteValidateMode getSingleton() {
		return singleton;
	}
	
	private DeleteValidateMode() {}

	@Override
	public ValidationResult validate(Map<String, Object> objectMap, TableMetadata table) {
		ValidationResult result = null;
		if(table.getPrimaryKeyColumns_() == null) {
			for(ColumnMetadata column : table.getValidateColumns()) {
				if((result = column.getValidateHandler().validate(objectMap.get(column.getCode()))) != null) 
					return result;
			}
		}else { // 有主键时的删除验证, 验证主键值; 前提是主键配置了需要被验证, 即validate=true
			for (ColumnMetadata pkColumn : table.getPrimaryKeyColumns_().values()) {
				if(pkColumn.getValidateHandler() != null && (result = pkColumn.getValidateHandler().validate(objectMap.get(pkColumn.getCode()))) != null)
					return result;
			}
		}
		return null;
	}

	@Override
	public Mode getMode() {
		return Mode.DELETE;
	}
}
