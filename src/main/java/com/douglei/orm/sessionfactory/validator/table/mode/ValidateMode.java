package com.douglei.orm.sessionfactory.validator.table.mode;

import java.util.Map;

import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * table数据的验证模式
 * @author DougLei
 */
public interface ValidateMode {
	
	/**
	 * 验证数据
	 * @param objectMap
	 * @param table
	 * @return
	 */
	ValidationResult validate(Map<String, Object> objectMap, TableMetadata table);
	
	/**
	 * 获取模式
	 * @return
	 */
	Mode getMode();
}
