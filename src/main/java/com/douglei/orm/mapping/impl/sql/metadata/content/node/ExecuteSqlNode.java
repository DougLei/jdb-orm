package com.douglei.orm.mapping.impl.sql.metadata.content.node;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;
import com.douglei.orm.sql.statement.entity.InputSqlParameter;

/**
 * 可执行的sql node
 * @author DougLei
 */
public class ExecuteSqlNode {
	private static final ExecuteSqlNode EMPTY_EXECUTE_SQL_NODE = new ExecuteSqlNode("", null, null);
	private String content;
	private List<SqlParameterMetadata> parameters; // sql参数集合
	private List<Object> parameterValues; // 执行sql语句对应的参数值集合
	
	public ExecuteSqlNode(PurposeEntity purposeEntity, String content, List<SqlParameterMetadata> sqlParameterByDefinedOrders, Object sqlParameter, String previousAlias) {
		if(sqlParameterByDefinedOrders != null) {
			for (SqlParameterMetadata parameter : sqlParameterByDefinedOrders) {
				if(parameter.isUsePlaceholder()) {
					if(purposeEntity.isGetParameterValues()) {
						if(parameterValues == null) 
							parameterValues = new ArrayList<Object>();
						parameterValues.add(new InputSqlParameter(parameter.getValue(sqlParameter, previousAlias), parameter.getDBDataType()));
					}
				}else {
					// 非占位符, 将实际值替换到变量的位置
					content = content.replaceAll(parameter.getConfigHolder().getPrefix() + parameter.getName() + parameter.getConfigHolder().getSuffix(), parameter.getValuePrefix() + parameter.getValue(sqlParameter, previousAlias) + parameter.getValueSuffix());
				}
			}
		}
		this.content = content;
		this.parameters = purposeEntity.isGetParameters()?sqlParameterByDefinedOrders:null;
	}
	
	public ExecuteSqlNode(String finalContent, List<SqlParameterMetadata> parameters, List<Object> parameterValues) {
		this.content = finalContent;
		this.parameters = parameters;
		this.parameterValues = parameterValues;
	}

	/**
	 * 获取一个空的ExecuteSqlNode实例
	 * @return
	 */
	public static ExecuteSqlNode emptyExecuteSqlNode() {
		return EMPTY_EXECUTE_SQL_NODE;
	}
	
	public String getContent() {
		return content;
	}
	public List<SqlParameterMetadata> getParameters(){
		return parameters;
	}
	public boolean existsParameters() {
		return parameters != null;
	}
	public List<Object> getParameterValues() {
		return parameterValues;
	}
	public boolean existsParameterValues() {
		return parameterValues != null;
	}
}
