package com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate.content.node;

import org.w3c.dom.Node;

import com.douglei.orm.core.metadata.sql.content.node.SqlNode;

/**
 * 
 * @author DougLei
 */
public interface SqlNodeHandler {
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	SqlNode doHandler(Node node);
	
	/**
	 * @see org.w3c.dom.Node.getNodeName()
	 * @return
	 */
	String getNodeName();
	
	/**
	 * 获取属性值
	 * @param attributeNode
	 * @return
	 */
	default String getAttributeValue(Node attributeNode) {
		if(attributeNode == null) {
			return null;
		}
		return attributeNode.getNodeValue();
	}
}
