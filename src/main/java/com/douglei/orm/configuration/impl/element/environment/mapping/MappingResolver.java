package com.douglei.orm.configuration.impl.element.environment.mapping;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.io.SAXReader;

/**
 * 
 * @author DougLei
 */
class MappingResolver {
	private SAXReader saxReader; // SAXReader
	private DocumentBuilder documentBuilder; // DocumentBuilder
	
	private MappingResolver4Sql mappingResolver4Sql; // sql映射专用处理器
	
	/**
	 * @see MappingResolverContext#getSAXReader()
	 * @return
	 */
	public SAXReader getSAXReader() {
		if(saxReader == null)
			saxReader = new SAXReader();
		return saxReader;
	}
	
	/**
	 * @see MappingResolverContext#getDocumentBuilder()
	 * @return
	 * @throws ParserConfigurationException 
	 */
	public DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		if(documentBuilder == null)
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return documentBuilder;
	}
	
	/**
	 * 获取sql映射专用处理器
	 * @return
	 */
	public MappingResolver4Sql getMappingResolver4Sql() {
		if(mappingResolver4Sql == null)
			mappingResolver4Sql = new MappingResolver4Sql();
		return mappingResolver4Sql;
	}
}
