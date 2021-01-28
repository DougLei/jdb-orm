package com.douglei.orm.configuration.extend.option;

import java.util.List;

import org.dom4j.Element;

/**
 * 
 * @author DougLei
 */
public class ExtendOptions {

	/**
	 * 扩展项处理
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	public void handle(Element element) {
		if(element == null)
			return;
		List<Element> options = element.elements("option");
		if(options.isEmpty())
			return;
		
		options.forEach(option -> {
			if(!"false".equalsIgnoreCase(option.attributeValue("enabled")))
				Type.get(option.attributeValue("type")).handle(option.attributeValue("value"));
		});
	}
}
