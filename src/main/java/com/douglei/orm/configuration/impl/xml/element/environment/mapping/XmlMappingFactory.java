package com.douglei.orm.configuration.impl.xml.element.environment.mapping;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.XmlSqlMapping;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.table.XmlTableMapping;
import com.douglei.orm.context.XmlReaderContext;
import com.douglei.tools.utils.CloseUtil;

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
	 * @return
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Mapping newMappingInstance(String mappingConfigurationXmlFilePath) throws FileNotFoundException, DocumentException, SAXException, IOException {
		MappingType mappingType = MappingType.toValueByMappingConfigurationFileName(mappingConfigurationXmlFilePath);
		logger.debug("开始解析映射配置文件[{}], 映射类型为[{}]", mappingConfigurationXmlFilePath, mappingType);
		return newMappingInstance(mappingType, mappingConfigurationXmlFilePath, new FileInputStream(mappingConfigurationXmlFilePath));
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
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Mapping newMappingInstance(MappingType mappingType, String mappingConfigurationContent) throws DocumentException, SAXException, IOException {
		logger.debug("开始解析映射配置文件[{}], 映射类型为[{}]", mappingConfigurationContent, mappingType);
		return newMappingInstance(mappingType, mappingConfigurationContent, new ByteArrayInputStream(mappingConfigurationContent.getBytes("utf-8")));
	}
	
	/**
	 * 创建mapping实例
	 * @param mappingType
	 * @param mappingConfigurationXmlName
	 * @param input
	 * @return
	 * @throws DocumentException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static Mapping newMappingInstance(MappingType mappingType, String mappingConfigurationXmlName, InputStream input) throws DocumentException, SAXException, IOException {
		try {
			switch(mappingType) {
				case TABLE:
					org.dom4j.Document tableDocument = XmlReaderContext.getTableMappingReader().read(input);
					org.dom4j.Element tableRootElement = tableDocument.getRootElement();
					return new XmlTableMapping(mappingConfigurationXmlName, tableRootElement);
				case SQL:
					org.w3c.dom.Document sqlDocument = XmlReaderContext.getSqlMappingReader().parse(input);
					org.w3c.dom.Element sqlRootElement = sqlDocument.getDocumentElement();
					return new XmlSqlMapping(mappingConfigurationXmlName, sqlRootElement);
			}
			throw new IllegalArgumentException("mappingType 没有匹配");
		} finally {
			CloseUtil.closeIO(input);
		}
	}
}
