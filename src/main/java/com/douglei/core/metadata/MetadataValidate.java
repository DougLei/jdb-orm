package com.douglei.core.metadata;

/**
 * 
 * @author DougLei
 */
public interface MetadataValidate {
	
	/**
	 * 对metadata验证，如果验证通过，则返回对应的Metadata实例
	 * @param obj
	 * @return
	 * @throws MetadataValidateException
	 */
	Metadata doValidate(Object obj) throws MetadataValidateException;
}
