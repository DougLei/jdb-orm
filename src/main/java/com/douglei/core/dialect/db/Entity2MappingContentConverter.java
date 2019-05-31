package com.douglei.core.dialect.db;

/**
 * 实体到映射内容的转换器
 * @author DougLei
 */
public interface Entity2MappingContentConverter {
	
	/**
	 * 转换为xml映射内容
	 * @return
	 */
	String toXmlMappingContent();
}
