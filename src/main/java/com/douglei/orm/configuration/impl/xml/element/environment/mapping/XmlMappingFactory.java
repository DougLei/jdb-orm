package com.douglei.orm.configuration.impl.xml.element.environment.mapping;

import java.io.ByteArrayInputStream;
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
import com.douglei.tools.utils.file.FileReaderUtil;

/**
 * 
 * @author DougLei
 */
public class XmlMappingFactory {
	private static final Logger logger = LoggerFactory.getLogger(XmlMappingFactory.class);
	
	/**
	 * 指定映射配置的xml文件路径, 读取并创建mapping实例
	 * @param mappingConfigurationXmlFilePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Mapping newMappingInstance(String mappingConfigurationXmlFilePath) throws DocumentException, SAXException, IOException {
		MappingType mappingType = MappingType.toValueByMappingConfigurationFileName(mappingConfigurationXmlFilePath);
		logger.debug("开始解析映射配置文件[{}], 映射类型为[{}]", mappingConfigurationXmlFilePath, mappingType);
		return newMappingInstance(mappingType, mappingConfigurationXmlFilePath, FileReaderUtil.readByPath(mappingConfigurationXmlFilePath));
	}
	
	/**
	 * 指定映射配置的content, 读取并创建mapping实例
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
