package com.douglei.orm.configuration.impl.xml.element.properties;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.SelfCheckingException;
import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.impl.xml.util.Dom4jElementUtil;
import com.douglei.tools.utils.CloseUtil;
import com.douglei.tools.utils.StringUtil;

/**
 * <properties>节点
 * @author DougLei
 */
public class Properties implements SelfProcessing{
	private static final Logger logger = LoggerFactory.getLogger(Properties.class);
	
	private ClassLoader loader;
	
	private String placeholderPrefix="${";
	private String placeholderSuffix="}";
	
	private Map<String, String> properties = new HashMap<String, String>();
	
	private ClassLoader getLoader() {
		if(loader == null) {
			loader = Properties.class.getClassLoader();
		}
		return loader;
	}
	
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
			String path = null;
			InputStream in = null;
			java.util.Properties juproperties = new java.util.Properties();
			Set<Object> keys = null;
			String keyString = null;
			
			for (Element element : resourceElements) {
				path = element.attributeValue("path");
				if(StringUtil.notEmpty(path) && path.endsWith(".properties")) {
					try {
						in = getLoader().getResourceAsStream(path.trim());
						juproperties.load(in);
						keys = juproperties.keySet();
						if(keys != null && keys.size() > 0) {
							for (Object key : keys) {
								keyString = key.toString();
								setProperties(placeholderPrefix + keyString + placeholderSuffix, juproperties.getProperty(keyString));
							}
							keys.clear();
						}
					} catch (Exception e) {
						throw new ProcessPropertiesElementException("读取<resource path=\""+path+"\" /> 的properties文件时出现异常/>", e); 
					} finally {
						CloseUtil.closeIO(in);
						juproperties.clear();
					}
				}
			}
		}
	}

	private void setProperties(String key, String value) {
		logger.debug("setProperties: key={}, value={}", key, value);
		properties.put(key, value);
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
		logger.debug("{} 开始 destroy", getClass());
		if(properties.size() > 0) {
			properties.clear();
		}
		logger.debug("{} 结束 destroy", getClass());
	}

	@Override
	public void selfChecking() throws SelfCheckingException {
	}
}
