package com.douglei.configuration.impl.xml.element.environment.mapping;

import java.util.Arrays;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.Mapping;
import com.douglei.configuration.environment.mapping.MappingType;
import com.douglei.configuration.impl.xml.element.environment.mapping.table.XmlTableMapping;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlMappingFactory {
	private static final Logger logger = LoggerFactory.getLogger(XmlMappingFactory.class);
	
	/**
	 * 根据xml创建mapping实例
	 * @param configFileName
	 * @param xmlDocument
	 * @return
	 */
	public static Mapping newInstanceByXml(String configFileName, Document xmlDocument) {
		Element rootElement = xmlDocument.getRootElement();
		String type = rootElement.attributeValue("type");
		if(StringUtil.isEmpty(type)) {
			throw new NullPointerException("在文件["+configFileName+"]中, <mapping-configuration>中的type属性值不能为空, 目前支持的值包括: [" + Arrays.toString(MappingType.values())+"]");
		}
		logger.debug("开始解析映射配置文件[{}], 映射类型为[{}]", configFileName, type);
		
		switch(MappingType.toValue(type)) {
			case TABLE:
				return new XmlTableMapping(configFileName, rootElement);
			case SQL:
				// TODO 还未实现
				return null;
		}
		return null;
	}
}
