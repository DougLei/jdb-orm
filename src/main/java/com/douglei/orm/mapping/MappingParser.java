package com.douglei.orm.mapping;

import java.io.InputStream;

import org.dom4j.Element;

/**
 * 
 * @author DougLei
 */
public abstract class MappingParser<T extends Mapping> {
	
	/**
	 * 解析出mapping实例
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public abstract T parse(InputStream input) throws Exception;
	
	/**
	 * 获取表映射属性实例
	 * @param rootElement
	 * @param code
	 * @param type
	 * @return
	 */
	protected MappingProperty getMappingProperty4Dom4j(Element rootElement, String code, String type) {
		MappingProperty property = new MappingProperty(code, type);
		Element propertyElement = rootElement.element("property");
		if(propertyElement != null)
			property.setValues(propertyElement.attributeValue("order"), propertyElement.attributeValue("supportCover"), propertyElement.attributeValue("supportDelete"), propertyElement.attributeValue("extendExpr"));
		return property;
	}
}
