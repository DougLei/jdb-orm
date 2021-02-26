package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.AbstractNestingNode;

/**
 * 
 * @author DougLei
 */
public abstract class SqlNodeParser {
	
	/**
	 * 
	 * @param node
	 * @return
	 * @throws SqlNodeParserException
	 */
	public abstract SqlNode parse(Node node) throws SqlNodeParserException;
	
	/**
	 * 当前解析器可以解析的节点类型
	 * @return
	 */
	public abstract SqlNodeType getNodeType();
	
	/**
	 * 获取属性值
	 * @param attributeNode
	 * @return
	 */
	public final String getAttributeValue(Node attributeNode) {
		if(attributeNode == null) 
			return null;
		return attributeNode.getNodeValue();
	}
	
	/**
	 * 给指定的sql节点设置子节点集合
	 * @param current
	 * @param childrens
	 */
	public final void setChildrenSqlNodes(AbstractNestingNode current, Node currentNode) {
		NodeList childrens = currentNode.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) 
			throw new SqlNodeParserException("<"+current.getType().getNodeName()+">中不存在任何配置");
		
		SqlNode c_sn = null;
		for(int i=0;i<cl;i++) {
			c_sn = SqlNodeParserContainer.parse(childrens.item(i));
			if(c_sn == null)
				continue;
			
			validateChildrenSqlNode(c_sn);
			current.addSqlNode(c_sn);
		}
		
		if(!current.existsSqlNode())
			throw new SqlNodeParserException("<"+current.getType().getNodeName()+">中不存在任何配置");
	}
	
	/**
	 * 验证sql子节点, 如果验证失败则抛出异常
	 * @param children
	 * @throws SqlNodeParserException
	 */
	protected void validateChildrenSqlNode(SqlNode children) throws SqlNodeParserException{
	}
}
