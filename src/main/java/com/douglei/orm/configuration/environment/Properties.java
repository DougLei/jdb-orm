package com.douglei.orm.configuration.environment;

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
	private Map<String, String> properties;
	
	@SuppressWarnings("unchecked")
	public Properties(Element element) {
		logger.debug("开始处理<properties>元素");
		if(element != null) {
			// 设置占位符前后缀字符
			String placeholderPrefix = element.attributeValue("placeholderPrefix", "${");
			String placeholderSuffix = element.attributeValue("placeholderSuffix", "}");
			logger.debug("placeholderPrefix is {}, placeholderSuffix is {}", placeholderPrefix, placeholderSuffix);
			
			setProperties(placeholderPrefix, placeholderSuffix, element.elements("resource"));// 读取并set
		}
		logger.debug("处理<properties>元素结束");
	}
	
	/**
	 * 设置读取出的属性KV
	 * @param placeholderPrefix
	 * @param placeholderSuffix
	 * @param resourceElements
	 */
	private void setProperties(String placeholderPrefix, String placeholderSuffix, List<Element> resourceElements) {
		if(resourceElements.isEmpty())
			return;
		
		String path;
		boolean decryptValue;
		PropertiesReader reader;;
		for (Element element : resourceElements) {
			path = element.attributeValue("path");
			if(StringUtil.isEmpty(path) || !path.endsWith(".properties"))
				continue;
			
			reader = new PropertiesReader(path);
			if(reader.isEmpty())
				continue;
			
			if(properties == null)
				properties = new HashMap<String, String>();
			
			decryptValue = Boolean.parseBoolean(element.attributeValue("decryptValue"));
			for (Entry<Object, Object> entry : reader.entrySet()) {
				if(logger.isDebugEnabled()) 
					logger.debug("setProperties: key={}, value={}", placeholderPrefix + entry.getKey().toString() + placeholderSuffix, decryptValue(entry.getValue(), decryptValue));
				
				properties.put(placeholderPrefix + entry.getKey().toString() + placeholderSuffix, decryptValue(entry.getValue(), decryptValue));
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
		if(properties != null && StringUtil.unEmpty(key)) 
			value = properties.get(key);
		logger.debug("key is {}, getValue is {}", key, value);
		return value;
	}
}
