package com.douglei.orm.configuration.extend.option;

import java.util.List;

import org.dom4j.Element;

import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class ExtendOptions {

	/**
	 * 扩展性处理
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
			String enabled = option.attributeValue("enabled");
			if(StringUtil.isEmpty(enabled) || Boolean.parseBoolean(enabled))
				Type.get(option.attributeValue("type")).handle(option.attributeValue("value"));
		});
	}
}
