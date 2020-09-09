package com.douglei.orm.configuration.impl.util;

import org.dom4j.io.SAXReader;

import com.douglei.orm.configuration.impl.element.environment.mapping.MappingResolverContext;

/**
 * xml读取器上下文
 * @author DougLei
 */
public class XmlReaderContext {
	
	/**
	 * 获取xml阅读器
	 * @return
	 */
	public static SAXReader getXmlReader() {
		return MappingResolverContext.getTableMappingReader();
	}
	
	/**
	 * 销毁
	 */
	public static void destroy() {
		MappingResolverContext.destroy();
	}
}
