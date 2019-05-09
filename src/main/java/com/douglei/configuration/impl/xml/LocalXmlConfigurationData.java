package com.douglei.configuration.impl.xml;

import org.dom4j.io.SAXReader;

/**
 * 当前xml配置相关data对象
 * @author DougLei
 */
public class LocalXmlConfigurationData {
	private static final ThreadLocal<SAXReader> mappingSAXReader = new ThreadLocal<SAXReader>();// 读取xml映射文件的SAXReader实例
	
	public static SAXReader getMappingSAXReader() {
		SAXReader reader = mappingSAXReader.get();
		if(reader == null) {
			reader = new SAXReader();
			mappingSAXReader.set(reader);
		}
		return reader;
	}
}

