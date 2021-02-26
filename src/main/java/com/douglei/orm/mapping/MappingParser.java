package com.douglei.orm.mapping;

import java.io.InputStream;

import org.dom4j.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.environment.EnvironmentContext;

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
		if(EnvironmentContext.getEnvironment().getMappingHandler().getMappingConfiguration().isEnableProperty()) {
			MappingProperty mappingProperty = new MappingProperty(mapping.getCode(), mapping.getType());
			
			// 解析MappingProperty配置
			Element propertyElement = rootElement.element("property");
			if(propertyElement != null)
				mappingProperty.setValues(propertyElement.attributeValue("order"), propertyElement.attributeValue("supportCover"), propertyElement.attributeValue("supportDelete"), propertyElement.attributeValue("extend"));
			
			return new MappingSubject(mappingProperty, mapping); 
		}
		return new MappingSubject(null, mapping);
	}
	
	/**
	 * 使用DomcumentBuilder构建映射主体实例
	 * @param mapping
	 * @param propertyNodeList
	 * @return
	 */
	protected final MappingSubject buildMappingSubjectByDocumentBuilder(Mapping mapping, org.w3c.dom.Element rootElement) {
		if(EnvironmentContext.getEnvironment().getMappingHandler().getMappingConfiguration().isEnableProperty()) {
			MappingProperty mappingProperty = new MappingProperty(mapping.getCode(), mapping.getType());
			
			// 解析MappingProperty配置
			NodeList propertyNodeList = rootElement.getElementsByTagName("property");
			if(propertyNodeList != null && propertyNodeList.getLength() > 0) {
				Node propertyNode = propertyNodeList.item(0);
				if(propertyNode.hasAttributes()) {
					NamedNodeMap attributeMap = propertyNode.getAttributes();
					mappingProperty.setValues(getValue(attributeMap.getNamedItem("order")), getValue(attributeMap.getNamedItem("supportCover")), getValue(attributeMap.getNamedItem("supportDelete")), getValue(attributeMap.getNamedItem("extend")));
				}
			}
			return new MappingSubject(mappingProperty, mapping); 
		}
		return new MappingSubject(null, mapping);
	}
	private String getValue(Node attributeNode) {
		if(attributeNode == null)
			return null;
		return attributeNode.getNodeValue();
	}
}
