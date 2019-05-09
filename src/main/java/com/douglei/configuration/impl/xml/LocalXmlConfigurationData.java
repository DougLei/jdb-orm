package com.douglei.configuration.impl.xml;

import org.dom4j.io.SAXReader;

/**
 * 当前xml配置相关data对象
 * @author DougLei
 */
public class LocalXmlConfigurationData {
	private static final ThreadLocal<Data> data = new ThreadLocal<Data>();
	
	private static Data getData() {
		Data d = data.get();
		if(d == null) {
			d = new Data();
			data.set(d);
		}
		return d;
	}
	
	public static SAXReader getConfigurationSAXReader() {
		Data data = getData();
		if(data.configurationSAXReader == null) {
			data.configurationSAXReader = new SAXReader();
		}
		return data.configurationSAXReader;
	}
	public static SAXReader getMappingSAXReader() {
		Data data = getData();
		if(data.mappingSAXReader == null) {
			data.mappingSAXReader = new SAXReader();
		}
		return data.mappingSAXReader;
	}
}

class Data{
	SAXReader configurationSAXReader;// 读取xml configuration文件的SAXReader实例
	SAXReader mappingSAXReader;// 读取xml映射文件的SAXReader实例
}

