package com.douglei.orm.core.metadata;

/**
 * 
 * @author DougLei
 */
public interface MetadataValidate<P, R extends Metadata> {
	
	/**
	 * 对metadata验证，如果验证通过，则返回对应的Metadata实例
	 * @param p
	 * @return
	 * @throws MetadataValidateException
	 */
	R doValidate(P p) throws MetadataValidateException;
}
