package com.douglei.configuration.impl.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.io.SAXReader;

/**
 * 当前系统进行xml配置解析时XMLReader对象
 * @author DougLei
 */
public class LocalXmlConfigurationXMLReader {
	private static final ThreadLocal<XmlReader> XML_READER = new ThreadLocal<XmlReader>();
	
	private static XmlReader getXmlReader() {
		XmlReader xmlReader = XML_READER.get();
		if(xmlReader == null) {
			xmlReader = new XmlReader();
			XML_READER.set(xmlReader);
		}
		return xmlReader;
	}
	
	public static SAXReader getConfigurationSAXReader() {
		XmlReader xmlReader = getXmlReader();
		if(xmlReader.configurationSAXReader == null) {
			xmlReader.configurationSAXReader = new SAXReader();
		}
		return xmlReader.configurationSAXReader;
	}
	public static SAXReader getTableMappingReader() {
		XmlReader xmlReader = getXmlReader();
		if(xmlReader.tableMappingSAXReader == null) {
			xmlReader.tableMappingSAXReader = new SAXReader();
		}
		return xmlReader.tableMappingSAXReader;
	}
	public static DocumentBuilder getSqlMappingReader() {
		XmlReader xmlReader = getXmlReader();
		if(xmlReader.sqlMappingDocumentBuilder == null) {
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				xmlReader.sqlMappingDocumentBuilder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				throw new BuildDocumentBuilderInstanceException(e);
			}
		}
		return xmlReader.sqlMappingDocumentBuilder;
	}
}

class XmlReader{
	SAXReader configurationSAXReader;// 读取xml configuration文件的SAXReader实例
	SAXReader tableMappingSAXReader;// 读取table-xml映射文件的SAXReader实例
	DocumentBuilder sqlMappingDocumentBuilder;// 读取sql-xml映射文件的DocumentBuilder实例
}