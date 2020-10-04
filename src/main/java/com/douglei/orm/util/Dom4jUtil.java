package com.douglei.orm.util;

import org.dom4j.Element;

/**
 * 
 * @author DougLei
 */
public class Dom4jUtil {
	
	/**
	 * 获取指定name的元素, 如果不存在则抛出异常
	 * @param name
	 * @param element
	 * @return
	 */
	public static Element getElement(String name, Element element) {
		Element targetElement = element.element(name);
		if(targetElement == null) 
			throw new NullPointerException("没有配置<"+name+">元素");
		return targetElement;
	}
}
