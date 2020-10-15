package com.douglei.orm.mapping;

import java.io.InputStream;

import org.dom4j.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

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
	 * 通过dom4j解析feature元素
	 * @param code
	 * @param mappingType
	 * @param featureElement
	 * @return
	 */
	protected final MappingFeature parseFeature(String code, String mappingType, Element featureElement) {
		MappingFeature feature = new MappingFeature(code, mappingType);
		feature.setValues(featureElement.attributeValue("supportCover"), featureElement.attributeValue("supportDelete"), featureElement.attributeValue("extendExpr"));
		return feature;
	}
	
	/**
	 * 通过DocumentBuilder解析feature元素
	 * @param code
	 * @param mappingType
	 * @param featureNode
	 * @return
	 */
	protected final MappingFeature parseFeature(String code, String mappingType, Node featureNode) {
		if(!featureNode.hasAttributes())
			return null;
		
		MappingFeature feature = new MappingFeature(code, mappingType);
		NamedNodeMap attributeMap = featureNode.getAttributes();
		feature.setValues(getValue(attributeMap.getNamedItem("supportCover")), getValue(attributeMap.getNamedItem("supportDelete")), getValue(attributeMap.getNamedItem("extendExpr")));
		return feature;
	}
	private String getValue(Node attributeNode) {
		if(attributeNode == null)
			return null;
		return attributeNode.getNodeValue();
	}
}
