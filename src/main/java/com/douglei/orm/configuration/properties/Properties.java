package com.douglei.orm.configuration.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.mini.app.crypto.Decryptor;
import com.douglei.tools.StringUtil;
import com.douglei.tools.file.reader.PropertiesReader;

/**
 * <properties>节点
 * @author DougLei
 */
public class Properties {
	private static final Logger logger = LoggerFactory.getLogger(Properties.class);
	
	private String placeholderPrefix="${";
	private String placeholderSuffix="}";
	
	private Map<String, String> properties = new HashMap<String, String>(8);
	
	@SuppressWarnings("unchecked")
	public Properties(Element element) {
		logger.debug("开始处理<properties>元素");
		if(element != null) {
			processPlaceholderCharacter(element);// 处理占位符前后缀字符
			setProperties(element.elements("resource"));// 读取并set
		}
		logger.debug("处理<properties>元素结束");
	}
	
	/**
	 * 处理占位符前后缀标识
	 * @param propertiesElement
	 */
	private void processPlaceholderCharacter(Element element) {
		String placeholderCharacter = element.attributeValue("placeholderPrefix");
		if(StringUtil.unEmpty(placeholderCharacter)) 
			placeholderPrefix = placeholderCharacter;
		logger.debug("placeholderPrefix is {}", placeholderPrefix);
		
		placeholderCharacter = element.attributeValue("placeholderSuffix");
		if(StringUtil.unEmpty(placeholderCharacter)) 
			placeholderSuffix = placeholderCharacter;
		logger.debug("placeholderSuffix is {}", placeholderSuffix);
	}
	
	/**
	 * 读取并set
	 * @param resourceElements
	 */
	private void setProperties(List<Element> resourceElements) {
		if(resourceElements.isEmpty())
			return;
		
		String path;
		boolean decryptValue;
		PropertiesReader reader;;
		for (Element element : resourceElements) {
			path = element.attributeValue("path");
			if(StringUtil.unEmpty(path) && path.endsWith(".properties")) {
				reader = new PropertiesReader(path);
				if(!reader.isEmpty()) {
					decryptValue = Boolean.parseBoolean(element.attributeValue("decryptValue"));
					for (Entry<Object, Object> entry : reader.entrySet()) {
						if(logger.isDebugEnabled()) 
							logger.debug("setProperties: key={}, value={}", placeholderPrefix + entry.getKey().toString() + placeholderSuffix, decryptValue(entry.getValue(), decryptValue));
						
						properties.put(placeholderPrefix + entry.getKey().toString() + placeholderSuffix, decryptValue(entry.getValue(), decryptValue));
					}
				}
			}
		}
	}
	
	/**
	 * 解密value值
	 * @param value
	 * @param decryptValue
	 * @return
	 */
	private Decryptor valueDecryptor;
	private String decryptValue(Object value, boolean decryptValue) {
		if(decryptValue) {
			if(valueDecryptor == null) 
				valueDecryptor = new Decryptor();
			return valueDecryptor.decrypt(value.toString());
		}
		return value.toString();
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		String value = null;
		if(StringUtil.unEmpty(key) && !properties.isEmpty()) 
			value = properties.get(key);
		logger.debug("key is {}, getValue is {}", key, value);
		return value;
	}
	
	/**
	 * 清空
	 */
	public void clear() {
		if(!properties.isEmpty()) 
			properties.clear();
	}
}
