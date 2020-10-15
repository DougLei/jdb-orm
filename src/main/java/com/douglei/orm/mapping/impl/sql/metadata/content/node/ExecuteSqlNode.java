package com.douglei.orm.mapping.impl.sql.metadata.content.node;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.sql.statement.entity.InputSqlParameter;

/**
 * 可执行的sql node
 * @author DougLei
 */
public class ExecuteSqlNode {
	private static final ExecuteSqlNode EMPTY_EXECUTE_SQL_NODE = new ExecuteSqlNode("", null);
	private String content;
	private List<Object> parameters; // 当前执行sql语句对应的参数值集合
	
	public ExecuteSqlNode(String content, List<SqlParameterMetadata> sqlParameterByDefinedOrders, Object sqlParameter, String sqlParameterNamePrefix) {
		if(sqlParameterByDefinedOrders != null) {
			Object parameterValue = null;
			for (SqlParameterMetadata parameter : sqlParameterByDefinedOrders) {
				parameterValue = parameter.getValue(sqlParameter, sqlParameterNamePrefix);
				if(parameter.isUsePlaceholder()) {
					if(parameters == null) 
						parameters = new ArrayList<Object>();
					parameters.add(new InputSqlParameter(parameterValue, parameter.getDBDataType()));
				}else {
					content = content.replaceAll(parameter.getConfigHolder().getPrefix() + parameter.getName() + parameter.getConfigHolder().getSuffix(), parameter.getValuePrefix() + parameterValue + parameter.getValueSuffix());
				}
			}
		}
		this.content = content;
	}
	
	public ExecuteSqlNode(String finalContent, List<Object> parameters) {
		this.content = finalContent;
		this.parameters = parameters;
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
	
	public List<Object> getParameters(){
		return parameters;
	}
	
	public boolean existsParameter() {
		return parameters != null;
	}
}
