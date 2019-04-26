package com.douglei.configuration.impl.xml.util;

import java.util.List;

import org.dom4j.Element;

/**
 * element工具类
 * @author DougLei
 */
public class ElementUtil {
	
	/**
	 * 获取必须存在, 且唯一的元素对象
	 * @param elementDescription
	 * @param elements
	 * @return
	 */
	public static Element getNecessaryAndSingleElement(String elementDescription, List<?> elements) {
		if(elements == null || elements.size() == 0) {
			throw new NotExistsElementException("没有配置"+elementDescription+"元素");
		}
		if(elements.size() > 1) {
			throw new RepeatElementException(elementDescription+"元素最多只能配置一个");
		}
		return (Element) elements.get(0);
	}
	
}
