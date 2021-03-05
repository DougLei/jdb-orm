package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeType;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;
import com.douglei.tools.OgnlUtil;
import com.douglei.tools.datatype.DataTypeConvertUtil;

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
		this.open = open;
		this.separator = separator;
		this.close = close;
	}
	
	// 获取要foreach的集合/数组对象
	private boolean collectionFlag; // 标识是否处理过collection
	private Object getCollectionObject(Object sqlParameter, String previousAlias) {
		if(sqlParameter == null)
			return sqlParameter;
		if(sqlParameter instanceof Collection<?> || sqlParameter.getClass().isArray()) 
			return sqlParameter;
		
		if(previousAlias != null && !collectionFlag) {
			collectionFlag = true;
			if(collection.startsWith(previousAlias+'.'))
				collection = collection.substring(previousAlias.length()+1);
		}
		return OgnlUtil.getSingleton().getObjectValue(collection, sqlParameter);
	}
	
	/**
	 * 从参数中获取到要循环的数组, 如果是集合也给它转换成数组
	 * @param sqlParameter
	 * @param previousAlias
	 * @return
	 */
	private Object[] getArray(Object sqlParameter, String previousAlias) {
		Object collectionObject = getCollectionObject(sqlParameter, previousAlias);
		if(collectionObject instanceof Collection<?>) {
			Collection<?> tc = (Collection<?>) collectionObject;
			
			Iterator<?> it = tc.iterator();
			Object obj = it.next();
			if(DataTypeConvertUtil.isSimpleType(obj)) {
				Object[] array = new Object[tc.size()];
				Map<String, Object> map = null;
				int index = 0;
				do{
					map = new HashMap<String, Object>(2);
					map.put(this.alias, obj);
					array[index] = map;
					index++;
					
					if(it.hasNext()) {
						obj = it.next();
						continue;
					}
					break;
				}while(true);
				return array;
			}else {
				return tc.toArray();
			}
		}else if(collectionObject.getClass().isArray()) {
			return (Object[]) collectionObject;
		}
		return null; // 不会走到这里, 如果走到这里, 就是逻辑上有bug
	}
	
	@Override
	public boolean matching(Object sqlParameter, String previousAlias) {
		Object collectionObject = getCollectionObject(sqlParameter, previousAlias);
		if(collectionObject == null) 
			return false;
		if(collectionObject instanceof Collection<?>) 
			return !((Collection<?>) collectionObject).isEmpty();
		if(collectionObject.getClass().isArray()) 
			return ((Object[]) collectionObject).length > 0;
		throw new IllegalArgumentException("目前<foreach>中的collection属性, 只支持["+Collection.class.getName()+"类型]和[数组类型]的数据");
	}
	
	@Override
	public ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String previousAlias) {
		Object[] array = getArray(sqlParameter, previousAlias);
		List<String> contentList = null;
		List<SqlParameterMetadata> parameters = null;
		List<Object> parameterValues = null;
		
		ExecutableSqlNode executableSqlNode = null;
		for(int i=0;i<array.length;i++) {
			for (SqlNode sqlNode : sqlNodes) {
				if(sqlNode.matching(array[i], this.alias)) {
					executableSqlNode = sqlNode.getExecutableSqlNode(purposeEntity, array[i], this.alias);
					if(executableSqlNode.existsParameters()) {
						if(parameters == null)
							parameters = new ArrayList<SqlParameterMetadata>();
						parameters.addAll(executableSqlNode.getParameters());
					}
					if(executableSqlNode.existsParameterValues()) {
						if(parameterValues == null) 
							parameterValues = new ArrayList<Object>();
						parameterValues.addAll(executableSqlNode.getParameterValues());
					}
					
					if(contentList == null) 
						contentList = new ArrayList<String>(10);
					contentList.add(executableSqlNode.getContent());
				}
			}
		}
		
		if(contentList == null) 
			return ExecutableSqlNode.emptyExecutableSqlNode();
		
		StringBuilder sqlContent = new StringBuilder(100);
		sqlContent.append(open);
		
		int index = 0, lastIndex = (contentList.size()-1);
		for (String sc : contentList) {
			sqlContent.append(sc);
			if(index == lastIndex) 
				break;
			
			sqlContent.append(separator);
			index++;
		}
		
		sqlContent.append(close);
		return new ExecutableSqlNode(sqlContent.toString(), parameters, parameterValues);
	}
	
	@Override
	public ValidateFailResult validateParameter(Object sqlParameter, String previousAlias) {
		Object[] array = getArray(sqlParameter, previousAlias);
		ValidateFailResult result = null;
		for(int i=0;i<array.length;i++) {
			for (SqlNode sqlNode : sqlNodes) {
				if(sqlNode.matching(array[i], this.alias) && (result = sqlNode.validateParameter(array[i], this.alias)) != null) 
					return result;
			}
		}
		return null;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.FOREACH;
	}
}