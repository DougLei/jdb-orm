package com.douglei.orm.core.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.douglei.orm.core.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNodeType;
import com.douglei.orm.core.metadata.sql.content.node.UnsupportCollectionTypeException;
import com.douglei.tools.instances.ognl.OgnlHandler;
import com.douglei.tools.utils.datatype.converter.ConverterUtil;

/**
 * 
 * @author DougLei
 */
public class ForeachSqlNode extends AbstractNestingNode {
	private static final long serialVersionUID = 6771739195272626919L;
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
	
	private Object getCollectionObject(Object sqlParameter) {
		if(sqlParameter != null) {
			if(sqlParameter instanceof Collection<?> || sqlParameter.getClass().isArray()) {
				return sqlParameter;
			}
			return OgnlHandler.singleInstance().getObjectValue(collection, sqlParameter);
		}
		return null;
	}
	
	@Override
	public boolean matching(Object sqlParameter, String alias) {
		Object collectionObject = getCollectionObject(sqlParameter);
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
			throw new UnsupportCollectionTypeException("目前<foreach>元素中的collection属性, 只支持["+Collection.class.getName()+"类型], [数组类型]");
		}
		return true;
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Object sqlParameter, String sqlParameterNamePrefix) {
		Object collectionObject = getCollectionObject(sqlParameter);
		Object[] array = null;
		if(collectionObject instanceof Collection<?>) {
			Collection<?> tc = (Collection<?>) collectionObject;
			
			Iterator<?> it = tc.iterator();
			Object obj = it.next();
			if(ConverterUtil.isSimpleType(obj)) {
				array = new Object[tc.size()];
				
				Map<String, Object> map = null;
				int index = 0;
				do{
					map = new HashMap<String, Object>(1);
					map.put(alias, obj);
					array[index] = map;
					index++;
					
					if(it.hasNext()) {
						obj = it.next();
						continue;
					}
					break;
				}while(true);
			}else {
				array = tc.toArray();
			}
		}else if(collectionObject.getClass().isArray()) {
			array = (Object[]) collectionObject;
		}
		
		List<String> sqlContentList = null;
		List<Object> parameters = null;
		
		ExecuteSqlNode executeSqlNode = null;
		int length = array.length;
		for(int i=0;i<length;i++) {
			for (SqlNode sqlNode : sqlNodes) {
				if(sqlNode.matching(array[i], alias)) {
					if(sqlContentList == null) {
						sqlContentList = new ArrayList<String>(length*sqlNodes.size()/2);
					}
					
					executeSqlNode = sqlNode.getExecuteSqlNode(array[i], alias);
					if(executeSqlNode.existsParameter()) {
						if(parameters == null) {
							parameters = new ArrayList<Object>();
						}
						parameters.addAll(executeSqlNode.getParameters());
					}
					sqlContentList.add(executeSqlNode.getContent());
				}
			}
		}
		
		if(sqlContentList == null) {
			return new ExecuteSqlNode("", parameters);
		}
		StringBuilder sqlContent = new StringBuilder();
		sqlContent.append(open);
		
		short index = 0, lastIndex = (short) (sqlContentList.size()-1);
		for (String sc : sqlContentList) {
			sqlContent.append(sc);
			if(index == lastIndex) {
				break;
			}
			sqlContent.append(separator);
			index++;
		}
		
		sqlContent.append(close);
		return new ExecuteSqlNode(sqlContent.toString(), parameters);
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.FOREACH;
	}
}