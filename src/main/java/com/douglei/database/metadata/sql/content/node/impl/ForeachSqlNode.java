package com.douglei.database.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.w3c.dom.Node;

import com.douglei.database.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNodeType;
import com.douglei.instances.ognl.OgnlHandler;

/**
 * 
 * @author DougLei
 */
public class ForeachSqlNode implements SqlNode {
	
	private TextSqlNode content;
	
	private String collection;
	private String alias;
	
	private String open = "";
	private String separator = ",";
	private String close = "";
	
	public ForeachSqlNode(String content, String collection, Node aliasAttributeNode, Node openAttributeNode, Node separatorAttributeNode, Node closeAttributeNode) {
		this.content = new TextSqlNode(content);
		this.collection = collection;
		if(aliasAttributeNode != null) {
			alias = aliasAttributeNode.getNodeValue();
		}
		if(openAttributeNode != null) {
			open = openAttributeNode.getNodeValue();
		}
		if(separatorAttributeNode != null) {
			separator = separatorAttributeNode.getNodeValue();
		}
		if(closeAttributeNode != null) {
			close = closeAttributeNode.getNodeValue();
		}
	}
	
	@Override
	public boolean matching(Object sqlParameter) {
		return true;
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Object sqlParameter) {
		Object collectionObject = OgnlHandler.singleInstance().getObjectValue(collection, sqlParameter);
		if(collectionObject == null) {
			return null;
		}
		Object[] array = null;
		if(collectionObject instanceof Collection<?>) {
			array = ((Collection<?>) collectionObject).toArray();
		}else if(collectionObject.getClass().isArray()) {
			array = (Object[]) collectionObject;
		}else {
			throw new UnsupportCollectionTypeException("目前<foreach>元素中的collection属性, 只支持[java.util.Collection<E>类型], [数组类型]");
		}
		if(array.length == 0) {
			return null;
		}
		
		StringBuilder sqlContent = new StringBuilder();
		List<Object> parameters = null;
		sqlContent.append(open).append(" ");
		
		ExecuteSqlNode executeSqlNode = null;
		int length = array.length;
		for(int i=0;i<length;i++) {
			executeSqlNode = content.getExecuteSqlNode(array[i]);
			if(executeSqlNode.existsParameter()) {
				if(parameters == null) {
					parameters = new ArrayList<Object>();
				}
				parameters.addAll(executeSqlNode.getParameters());
			}
			sqlContent.append(executeSqlNode.getContent()).append(" ");
			
			if(i < length-1) {
				sqlContent.append(separator).append(" ");
			}
		}
		
		sqlContent.append(close).append(" ");
		return new ExecuteSqlNode(sqlContent.toString(), parameters);
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.FOREACH;
	}
}