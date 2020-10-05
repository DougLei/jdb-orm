package com.douglei.orm.mapping.impl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.io.SAXReader;

/**
 * 
 * @author DougLei
 */
class MappingParser {
	private SAXReader saxReader; // SAXReader
	private DocumentBuilder documentBuilder; // DocumentBuilder
	
	private MappingParser4Sql mappingParserSql; // sql映射专用处理器
	
	/**
	 * @see MappingParserContext#getSAXReader()
	 * @return
	 */
	public SAXReader getSAXReader() {
		if(saxReader == null)
			saxReader = new SAXReader();
		return saxReader;
	}
	
	/**
	 * @see MappingParserContext#getDocumentBuilder()
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
	public MappingParser4Sql getMappingParserSql() {
		if(mappingParserSql == null)
			mappingParserSql = new MappingParser4Sql();
		return mappingParserSql;
	}
}
