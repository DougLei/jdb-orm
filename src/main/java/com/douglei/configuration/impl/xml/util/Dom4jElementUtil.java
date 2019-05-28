package com.douglei.configuration.impl.xml.util;

import org.dom4j.Element;

/**
 * element工具类
 * @author DougLei
 */
public class Dom4jElementUtil {
	
	/**
	 * 验证元素是否存在
	 * @param elementName
	 * @param element
	 * @return
	 */
	public static Element validateElementExists(String elementName, Element element) {
		Element targetElement = element.element(elementName);
		if(targetElement == null) {
			throw new NotExistsElementException("没有配置<"+elementName+">元素");
		}
		return targetElement;
	}
}
