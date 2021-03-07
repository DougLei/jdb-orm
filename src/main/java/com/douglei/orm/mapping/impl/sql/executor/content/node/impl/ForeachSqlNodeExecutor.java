package com.douglei.orm.mapping.impl.sql.executor.content.node.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.douglei.orm.configuration.OrmException;
import com.douglei.orm.mapping.impl.sql.SqlNodeContainer;
import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.executor.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.executor.content.node.SqlNodeExecuteException;
import com.douglei.orm.mapping.impl.sql.executor.content.node.SqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ForeachSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ParameterNode;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;
import com.douglei.tools.OgnlUtil;
import com.douglei.tools.datatype.DataTypeValidateUtil;


/**
 * 
 * @author DougLei
 */
public class ForeachSqlNodeExecutor implements SqlNodeExecutor<ForeachSqlNode> {

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.FOREACH;
	}
	
	/**
	 * 获取集合实例
	 * @param currentSqlNode
	 * @param sqlParameter
	 * @param previousAlias
	 * @return
	 */
	private Object getCollectionObject(ForeachSqlNode currentSqlNode, Object sqlParameter, String previousAlias) {
		if(sqlParameter == null)
			return sqlParameter;
		if(sqlParameter instanceof Collection<?> || sqlParameter.getClass().isArray()) 
			return sqlParameter;
		
		String collection = currentSqlNode.getCollection();
		if(previousAlias != null && collection.startsWith(previousAlias+'.'))
			collection = collection.substring(previousAlias.length()+1);
		return OgnlUtil.getObjectValue(collection, sqlParameter);
	}
	
	/**
	 * 从参数中获取到要循环的数组, 如果是集合也给它转换成数组
	 * @param currentSqlNode
	 * @param sqlParameter
	 * @param previousAlias
	 * @return
	 */
	private Object[] getArray(ForeachSqlNode currentSqlNode, Object sqlParameter, String previousAlias) {
		Object collectionObject = getCollectionObject(currentSqlNode, sqlParameter, previousAlias);
		if(collectionObject instanceof Collection<?>) {
			Collection<?> tc = (Collection<?>) collectionObject;
			
			Iterator<?> it = tc.iterator();
			Object obj = it.next();
			if(DataTypeValidateUtil.isSimpleDataType(obj)) {
				Object[] array = new Object[tc.size()];
				Map<String, Object> map = null;
				int index = 0;
				do{
					map = new HashMap<String, Object>(2);
					map.put(currentSqlNode.getAlias(), obj);
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
		throw new OrmException("BUG");
	}
	
	@Override
	public boolean matching(ForeachSqlNode currentSqlNode, Object sqlParameter, String previousAlias) {
		Object collectionObject = getCollectionObject(currentSqlNode, sqlParameter, previousAlias);
		if(collectionObject == null) 
			return false;
		if(collectionObject instanceof Collection<?>) 
			return !((Collection<?>) collectionObject).isEmpty();
		if(collectionObject.getClass().isArray()) 
			return ((Object[]) collectionObject).length > 0;
		throw new SqlNodeExecuteException("目前<foreach>中的collection属性, 只支持["+Collection.class.getName()+"类型]和[数组类型]的数据");
	}

	@Override
	public ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, ForeachSqlNode currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Object sqlParameter, String previousAlias) {
		List<String> sqls = null;
		List<ParameterNode> parameters = null;
		List<Object> parameterValues = null;
		
		Object[] array = getArray(currentSqlNode, sqlParameter, previousAlias);
		for(int i=0;i<array.length;i++) {
			for (SqlNode sqlNode : currentSqlNode.getSqlNodes()) {
				SqlNodeExecutor<SqlNode> executor = SqlNodeContainer.getExecutor(sqlNode);
				if(executor.matching(sqlNode, array[i], currentSqlNode.getAlias())) {
					ExecutableSqlNode executableSqlNode = executor.getExecutableSqlNode(purposeEntity, sqlNode, sqlContentMetadataMap, array[i], currentSqlNode.getAlias());
					if(executableSqlNode.getParameters() != null) {
						if(parameters == null)
							parameters = new ArrayList<ParameterNode>();
						parameters.addAll(executableSqlNode.getParameters());
					}
					if(executableSqlNode.getParameterValues() != null) {
						if(parameterValues == null) 
							parameterValues = new ArrayList<Object>();
						parameterValues.addAll(executableSqlNode.getParameterValues());
					}
					
					if(sqls == null) 
						sqls = new ArrayList<String>();
					sqls.add(executableSqlNode.getSql());
				}
			}
		}
		
		if(sqls == null) 
			return ExecutableSqlNode.EMPTY_EXECUTABLE_SQL_NODE;
		
		StringBuilder sql = new StringBuilder(100);
		sql.append(currentSqlNode.getOpen());
		
		int index = 0, lastIndex = (sqls.size()-1);
		for (String s : sqls) {
			sql.append(s);
			if(index == lastIndex) 
				break;
			
			sql.append(currentSqlNode.getSeparator());
			index++;
		}
		
		sql.append(currentSqlNode.getClose());
		return new ExecutableSqlNode(sql.toString(), parameters, parameterValues);
	}

	@Override
	public ValidateFailResult validate(ForeachSqlNode currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Map<String, List<Validator>> validatorsMap, Object sqlParameter, String previousAlias) {
		Object[] array = getArray(currentSqlNode, sqlParameter, previousAlias);
		for(int i=0;i<array.length;i++) {
			for (SqlNode sqlNode : currentSqlNode.getSqlNodes()) {
				SqlNodeExecutor<SqlNode> executor = SqlNodeContainer.getExecutor(sqlNode);
				if(executor.matching(sqlNode, array[i], previousAlias)) {
					ValidateFailResult failResult = executor.validate(sqlNode, sqlContentMetadataMap, validatorsMap, array[i], previousAlias);
					if(failResult != null)
						return failResult;
				}
			}
		}
		return null;
	}
}
