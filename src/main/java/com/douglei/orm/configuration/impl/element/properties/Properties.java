package com.douglei.orm.configuration.impl.element.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.mini.app.crypto.Decryptor;
import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.impl.util.Dom4jElementUtil;
import com.douglei.tools.instances.file.reader.PropertiesReader;
import com.douglei.tools.utils.StringUtil;

/**
 * <properties>节点
 * @author DougLei
 */
public class Properties implements SelfProcessing{
	private static final Logger logger = LoggerFactory.getLogger(Properties.class);
	
	private String placeholderPrefix="${";
	private String placeholderSuffix="}";
	
	private Map<String, String> properties = new HashMap<String, String>(8);
	
	public Properties(Element propertiesElement) {
		logger.debug("开始处理<properties>元素");
		if(propertiesElement != null) {
			processPlaceholderCharacter(propertiesElement);// 处理占位符前后缀字符
			readAndSetProperties(Dom4jElementUtil.elements("resource", propertiesElement));// 读取并set
		}
		logger.debug("处理<properties>元素结束");
	}
	
	/**
	 * 处理占位符前后缀标识
	 * @param propertiesElement
	 */
	private void processPlaceholderCharacter(Element propertiesElement) {
		String placeholderCharacter = null;
		placeholderCharacter = propertiesElement.attributeValue("placeholderPrefix");
		if(StringUtil.notEmpty(placeholderCharacter)) {
			placeholderPrefix = placeholderCharacter;
		}
		logger.debug("placeholderPrefix is {}", placeholderPrefix);
		
		placeholderCharacter = propertiesElement.attributeValue("placeholderSuffix");
		if(StringUtil.notEmpty(placeholderCharacter)) {
			placeholderSuffix = placeholderCharacter;
		}
		logger.debug("placeholderSuffix is {}", placeholderSuffix);
	}
	
	/**
	 * 读取并set
	 * @param resourceElements
	 */
	private void readAndSetProperties(List<Element> resourceElements) {
		if(resourceElements != null) {
			String path;
			boolean decryptValue;
			PropertiesReader reader;;
			for (Element element : resourceElements) {
				path = element.attributeValue("path");
				if(StringUtil.notEmpty(path) && path.endsWith(".properties")) {
					reader = new PropertiesReader(path);
					if(reader.ready()) {
						decryptValue = Boolean.parseBoolean(element.attributeValue("decryptValue"));
						for (Entry<Object, Object> entry : reader.entrySet()) {
							if(logger.isDebugEnabled()) {
								logger.debug("setProperties: key={}, value={}", placeholderPrefix + entry.getKey().toString() + placeholderSuffix, decryptValue(entry.getValue(), decryptValue));
							}
							properties.put(placeholderPrefix + entry.getKey().toString() + placeholderSuffix, decryptValue(entry.getValue(), decryptValue));
						}
					}
				}
			}
		}
	}
	
	private Decryptor valueDecryptor;
	
	/**
	 * 解密value值
	 * @param value
	 * @param decryptValue
	 * @return
	 */
	private String decryptValue(Object value, boolean decryptValue) {
		if(decryptValue) {
			if(valueDecryptor == null) {
				valueDecryptor = new Decryptor();
			}
			return valueDecryptor.decrypt(value.toString());
		}
		return value.toString();
	}
	
	public String getProperties(String key) {
		String value = null;
		if(StringUtil.notEmpty(key) && properties.size() > 0) {
			value = properties.get(key);
		}
		logger.debug("key is {}, getValue is {}", key, value);
		return value;
	}
	
	@Override
	public void destroy() throws DestroyException{
		if(logger.isDebugEnabled()) logger.debug("{} 开始 destroy", getClass().getName());
		if(properties.size() > 0) {
			properties.clear();
		}
		if(logger.isDebugEnabled()) logger.debug("{} 结束 destroy", getClass().getName());
	}
}
