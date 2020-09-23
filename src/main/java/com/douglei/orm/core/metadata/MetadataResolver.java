package com.douglei.orm.core.metadata;

/**
 * 元数据解析器
 * @author DougLei
 */
public interface MetadataResolver<P, R extends Metadata> {
	
	/**
	 * 对metadata解析，如果解析成功，则返回对应的Metadata实例
	 * @param p
	 * @return
	 * @throws MetadataResolvingException
	 */
	R resolving(P p) throws MetadataResolvingException;
}
