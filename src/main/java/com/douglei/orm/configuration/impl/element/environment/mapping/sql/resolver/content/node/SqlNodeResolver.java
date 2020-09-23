package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node;

import org.w3c.dom.Node;

import com.douglei.orm.core.metadata.sql.content.node.SqlNode;

/**
 * 
 * @author DougLei
 */
public interface SqlNodeResolver {
	
	/**
	 * 
	 * @param node
	 * @return
	 */
	SqlNode resolving(Node node);
	
	/**
	 * 当前解析器可以解析的xml节点名
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
