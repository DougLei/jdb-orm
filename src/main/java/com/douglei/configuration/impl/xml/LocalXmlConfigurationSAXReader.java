package com.douglei.configuration.impl.xml;

import org.dom4j.io.SAXReader;

/**
 * 当前系统进行xml配置解析时SAXReader对象
 * @author DougLei
 */
public class LocalXmlConfigurationSAXReader {
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
	public static SAXReader getMappingSAXReader() {
		XmlReader xmlReader = getXmlReader();
		if(xmlReader.mappingSAXReader == null) {
			xmlReader.mappingSAXReader = new SAXReader();
		}
		return xmlReader.mappingSAXReader;
	}
}

class XmlReader{
	SAXReader configurationSAXReader;// 读取xml configuration文件的SAXReader实例
	SAXReader mappingSAXReader;// 读取xml映射文件的SAXReader实例
}

