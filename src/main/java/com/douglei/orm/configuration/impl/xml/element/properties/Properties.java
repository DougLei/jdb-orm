package com.douglei.orm.configuration.impl.xml.element.properties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.orm.configuration.impl.xml.util.Dom4jElementUtil;
import com.douglei.tools.instances.file.resources.reader.PropertiesReader;
import com.douglei.tools.utils.CryptographyUtil;
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
			boolean decodeValue;
			PropertiesReader reader = new PropertiesReader();
			for (Element element : resourceElements) {
				path = element.attributeValue("path");
				if(StringUtil.notEmpty(path) && path.endsWith(".properties")) {
					reader.setPath(path);
					if(reader.ready()) {
						decodeValue = Boolean.parseBoolean(element.attributeValue("decodeValue"));
						for (Entry<Object, Object> entry : reader.entrySet()) {
							if(logger.isDebugEnabled()) {
								logger.debug("setProperties: key={}, value={}", placeholderPrefix + entry.getKey().toString() + placeholderSuffix, getValue(entry.getValue(), decodeValue));
							}
							properties.put(placeholderPrefix + entry.getKey().toString() + placeholderSuffix, getValue(entry.getValue(), decodeValue));
						}
					}
				}
			}
		}
	}
	
	/**
	 * 获取value值
	 * @param value
	 * @param decodeValue
	 * @return
	 */
	private String getValue(Object value, boolean decodeValue) {
		if(decodeValue) {
			return CryptographyUtil.decodeByBASE64(value.toString().substring(20));
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
	
	/**
	 * 加密value值
	 * @param value
	 * @return
	 */
	public static String encodeValue(String value) {
		value = CryptographyUtil.encodeByBASE64(value);
		
		
		return value;
	}
	
	private static final char[] c = {'0','1','2','3','4','5','6','7','8','9','0','a','b','c','d','e','f','g','h','i','g','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','G','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	public static void main(String[] args) {
		String a = "{";
		String b = "01234567890abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < b.length(); i++) {
			a += "'" + b.charAt(i) + "',";
		}
		System.out.println(a);
	}
}
