package com.douglei.orm.mapping;

import java.io.InputStream;

import org.dom4j.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author DougLei
 */
public abstract class MappingParser {
	
	/**
	 * 解析出MappingSubject
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public abstract MappingSubject parse(InputStream input) throws Exception;
	
	/**
	 * 使用Dom4j构建映射主体实例
	 * @param mapping
	 * @param element
	 * @return
	 */
	protected final MappingSubject buildMappingSubjectByDom4j(Mapping mapping, Element rootElement) {
		MappingProperty property = new MappingProperty(mapping.getCode(), mapping.getType());
		
		// 解析MappingProperty配置
		Element propertyElement = rootElement.element("property");
		if(propertyElement != null)
			property.setValues(propertyElement.attributeValue("order"), propertyElement.attributeValue("supportCover"), propertyElement.attributeValue("supportDelete"), propertyElement.attributeValue("extend"));
		
		return new MappingSubject(property, mapping); 
	}
	
	/**
	 * 使用DomcumentBuilder构建映射主体实例
	 * @param mapping
	 * @param propertyNodeList
	 * @return
	 */
	protected final MappingSubject buildMappingSubjectByDocumentBuilder(Mapping mapping, org.w3c.dom.Element rootElement) {
		MappingProperty property = new MappingProperty(mapping.getCode(), mapping.getType());
		
		// 解析MappingProperty配置
		NodeList propertyNodeList = rootElement.getElementsByTagName("property");
		if(propertyNodeList != null && propertyNodeList.getLength() > 0) {
			Node propertyNode = propertyNodeList.item(0);
			if(propertyNode.hasAttributes()) {
				NamedNodeMap attributeMap = propertyNode.getAttributes();
				property.setValues(getValue(attributeMap.getNamedItem("order")), getValue(attributeMap.getNamedItem("supportCover")), getValue(attributeMap.getNamedItem("supportDelete")), getValue(attributeMap.getNamedItem("extend")));
			}
		}
		return new MappingSubject(property, mapping); 
	}
	private String getValue(Node attributeNode) {
		if(attributeNode == null)
			return null;
		return attributeNode.getNodeValue();
	}
}
