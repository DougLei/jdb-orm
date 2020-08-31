package com.douglei.orm.configuration.impl.util;

import java.util.List;

import org.dom4j.Element;

/**
 * 
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
	
	/**
	 * 获取element
	 * @param elementName
	 * @param root
	 * @return
	 */
	public static Element element(String elementName, Element root){
		if(root == null) {
			return null;
		}
		return root.element(elementName);
	}
	
	/**
	 * 获取element集合
	 * @param elementName
	 * @param root
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> elements(String elementName, Element root){
		if(root == null) {
			return null;
		}
		List<Element> elements = root.elements(elementName);
		if(elements == null || elements.isEmpty()) {
			return null;
		}
		return elements;
	}
}
