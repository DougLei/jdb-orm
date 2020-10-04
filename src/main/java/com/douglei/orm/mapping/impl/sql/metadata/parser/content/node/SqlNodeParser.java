package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;

/**
 * 
 * @author DougLei
 */
public interface SqlNodeParser {
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws SqlNodeParserException
	 */
	SqlNode parse(Node node) throws SqlNodeParserException;
	
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
