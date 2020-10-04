package com.douglei.orm.mapping.metadata.parser;

import com.douglei.orm.mapping.metadata.Metadata;

/**
 * 元数据解析器
 * @author DougLei
 */
public interface MetadataParser<P, R extends Metadata> {
	
	/**
	 * 对metadata解析，如果解析成功，则返回对应的Metadata实例
	 * @param p
	 * @return
	 * @throws MetadataParseException
	 */
	R parse(P p) throws MetadataParseException;
}
