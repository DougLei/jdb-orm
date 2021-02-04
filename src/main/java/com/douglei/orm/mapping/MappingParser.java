package com.douglei.orm.mapping;

import java.io.InputStream;

import org.dom4j.Element;

/**
 * 
 * @author DougLei
 */
public abstract class MappingParser<T extends Mapping> {
	
	/**
	 * 解析出MappingEntity
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public abstract MappingEntity<T> parse(InputStream input) throws Exception;
	
	/**
	 * 使用Dom4j, 获取MappingProperty
	 * @param rootElement
	 * @param code
	 * @param type
	 * @return
	 */
	protected final MappingProperty getMappingPropertyByDom4j(Element rootElement, String code, String type) {
		MappingProperty property = new MappingProperty(code, type);
		Element propertyElement = rootElement.element("property");
		if(propertyElement != null)
			property.setValues(propertyElement.attributeValue("order"), propertyElement.attributeValue("supportCover"), propertyElement.attributeValue("supportDelete"), propertyElement.attributeValue("extendExpr"));
		return property;
	}
}
