package com.douglei.orm.core.metadata.validator;

import com.douglei.orm.context.MappingConfigContext;

/**
 * 
 * @author DougLei
 */
public class DataValidateException extends RuntimeException{
	private static final long serialVersionUID = 5380937075624267851L;

	public DataValidateException(String descriptionName, String name, Object value, String message) {
		super(MappingConfigContext.getCurrentExecuteMappingDescription() + ", " + descriptionName + "["+name+"], 传入的值为[" + value + "], " + message);
	}
}
