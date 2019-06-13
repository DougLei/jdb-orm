package com.douglei.orm.core.dialect.db;

import com.douglei.orm.context.RunMappingConfigurationContext;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeValidateResult;

/**
 * 验证异常
 * @author DougLei
 */
public class ValidateException extends RuntimeException{

	public ValidateException(String descriptionName, String name, String message) {
		super(RunMappingConfigurationContext.getCurrentExecuteMappingDescription() + descriptionName + "["+name+"]" + message);
	}

	public ValidateException(String descriptionName, String name, DataTypeValidateResult result) {
		super(RunMappingConfigurationContext.getCurrentExecuteMappingDescription() + descriptionName + "["+name+"]" + "的数据类型不匹配, 应为"+result.getShouldDataType()+"类型, 实际为"+result.getActualDataType()+"类型");
	}
}
