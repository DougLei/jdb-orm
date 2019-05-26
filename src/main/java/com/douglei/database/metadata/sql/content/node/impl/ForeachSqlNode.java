package com.douglei.database.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.douglei.database.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNodeType;
import com.douglei.database.metadata.sql.content.node.UnsupportCollectionTypeException;
import com.douglei.instances.ognl.OgnlHandler;

/**
 * 
 * @author DougLei
 */
public class ForeachSqlNode extends AbstractNestingNode {
	
	private String collection;
	private String alias;
	
	private String open;
	private String separator;
	private String close;
	
	public ForeachSqlNode(String collection, String alias, String open, String separator, String close) {
		this.collection = collection;
		this.alias = alias;
		this.open = open==null?" ":open+" ";
		this.separator = separator==null?" ":separator;
		this.close = close==null?" ":" "+close;
	}
	
	@Override
	public boolean matching(Object sqlParameter, String alias) {
		Object collectionObject = OgnlHandler.singleInstance().getObjectValue(collection, sqlParameter);
		if(collectionObject == null) {
			return false;
		}
		if(collectionObject instanceof Collection<?>) {
			Collection<?> tc = (Collection<?>) collectionObject;
			if(tc.isEmpty()) {
				return false;
			}
		}else if(collectionObject.getClass().isArray()) {
			if(((Object[]) collectionObject).length == 0) {
				return false;
			}
		}else {
			throw new UnsupportCollectionTypeException("目前<foreach>元素中的collection属性, 只支持[java.util.Collection<E>类型], [数组类型]");
		}
		return true;
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Object sqlParameter, String sqlParameterNamePrefix) {
		Object collectionObject = OgnlHandler.singleInstance().getObjectValue(collection, sqlParameter);
		Object[] array = null;
		if(collectionObject instanceof Collection<?>) {
			array = ((Collection<?>) collectionObject).toArray();
		}else if(collectionObject.getClass().isArray()) {
			array = (Object[]) collectionObject;
		}
		
		StringBuilder sqlContent = new StringBuilder();
		List<Object> parameters = null;
		sqlContent.append(open);
		
		ExecuteSqlNode executeSqlNode = null;
		int length = array.length;
		for(int i=0;i<length;i++) {
			for (SqlNode sqlNode : sqlNodes) {
				if(sqlNode.matching(array[i], alias)) {
					executeSqlNode = sqlNode.getExecuteSqlNode(array[i], alias);
					if(executeSqlNode.existsParameter()) {
						if(parameters == null) {
							parameters = new ArrayList<Object>();
						}
						parameters.addAll(executeSqlNode.getParameters());
					}
					sqlContent.append(executeSqlNode.getContent()).append(" ");
				}
			}
			
			if(i < length-1) {
				sqlContent.append(separator);
			}
		}
		
		sqlContent.append(close);
		return new ExecuteSqlNode(sqlContent.toString(), parameters);
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.FOREACH;
	}
}