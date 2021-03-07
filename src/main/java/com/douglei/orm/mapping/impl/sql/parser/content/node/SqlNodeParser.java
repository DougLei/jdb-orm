package com.douglei.orm.mapping.impl.sql.parser.content.node;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.mapping.impl.sql.SqlNodeContainer;
import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.AbstractNestingNode;

/**
 * 
 * @author DougLei
 */
public abstract class SqlNodeParser {
	
	/**
	 * 
	 * @return
	 */
	public abstract SqlNodeType getType();
	
	/**
	 * 
	 * @param node
	 * @return 返回null, 会忽略当前SqlNode
	 * @throws SqlNodeParseException
	 */
	public abstract SqlNode parse(Node node) throws SqlNodeParseException;
	
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
			throw new SqlNodeParseException("<"+current.getType().getName()+">中不存在任何配置");
		
		SqlNode c_sn = null;
		for(int i=0;i<cl;i++) {
			c_sn = SqlNodeContainer.parse(childrens.item(i));
			if(c_sn == null)
				continue;
			
			validateChildrenSqlNode(c_sn);
			current.addSqlNode(c_sn);
		}
		
		if(!current.existsSqlNode())
			throw new SqlNodeParseException("<"+current.getType().getName()+">中不存在任何配置");
	}
	
	/**
	 * 验证sql子节点, 如果验证失败则抛出异常
	 * @param children
	 * @throws SqlNodeParseException
	 */
	protected void validateChildrenSqlNode(SqlNode children) throws SqlNodeParseException{
	}
}
