package com.douglei.configuration.impl.xml.element.environment.mapping;

import java.io.File;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.BuildMappingInstanceException;
import com.douglei.configuration.environment.mapping.Mapping;
import com.douglei.configuration.environment.mapping.MappingType;
import com.douglei.configuration.impl.xml.LocalXmlConfigurationXMLReader;
import com.douglei.configuration.impl.xml.element.environment.mapping.table.XmlTableMapping;

/**
 * 
 * @author DougLei
 */
public class XmlMappingFactory {
	private static final Logger logger = LoggerFactory.getLogger(XmlMappingFactory.class);
	
	/**
	 * <pre>
	 * 	根据xml创建mapping实例
	 * 	在初始化时, 加载配置文件时, 创建mapping实例的方法
	 * </pre>
	 * @param mappingConfigurationXmlFilePath
	 * @param mappingConfigurationXmlFile
	 * @return
	 * @throws DocumentException 
	 */
	public static Mapping newInstanceByXml_initial(String mappingConfigurationXmlFilePath) throws DocumentException {
		MappingType mappingType = MappingType.toValueByMappingConfigurationFileName(mappingConfigurationXmlFilePath);
		logger.debug("开始解析映射配置文件[{}], 映射类型为[{}]", mappingConfigurationXmlFilePath, mappingType);
		
		switch(mappingType) {
			case TABLE:
				Document xmlDocument = LocalXmlConfigurationXMLReader.getTableMappingReader().read(new File(mappingConfigurationXmlFilePath));
				Element rootElement = xmlDocument.getRootElement();
				return new XmlTableMapping(mappingConfigurationXmlFilePath, rootElement);
			case SQL:
				return null;
		}
		throw new BuildMappingInstanceException("没有匹配到对应的mappingType, 创建mapping实例失败");
	}
	
	
	/**
	 * <pre>
	 * 	根据xml创建mapping实例
	 * 	系统运行后, 动态添加新的映射文件时, 创建mapping实例的方法
	 * </pre>
	 * @param mappingType
	 * @param mappingConfigurationContent
	 * @return
	 * @throws DocumentException 
	 * @throws UnsupportedEncodingException 
	 */
	public static Mapping newInstanceByXml_dynamicAdd(MappingType mappingType, String mappingConfigurationContent) throws DocumentException {
		logger.debug("开始解析映射配置文件[{}], 映射类型为[{}]", mappingConfigurationContent, mappingType);
		
		switch(mappingType) {
			case TABLE:
				Document xmlDocument = LocalXmlConfigurationXMLReader.getTableMappingReader().read(new StringReader(mappingConfigurationContent));
				Element rootElement = xmlDocument.getRootElement();
				return new XmlTableMapping(mappingConfigurationContent, rootElement);
			case SQL:
				return null;
		}
		return null;
	}
}
