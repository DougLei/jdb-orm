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
	private List<Object> parameters; // 执行sql语句对应的参数值集合
	private List<SqlParameterMetadata> sqlParameters; // sql参数集合
	
	public ExecuteSqlNode(PurposeEntity purposeEntity, String content, List<SqlParameterMetadata> sqlParameterByDefinedOrders, Object sqlParameter, String alias) {
		if(sqlParameterByDefinedOrders != null) {
			for (SqlParameterMetadata parameter : sqlParameterByDefinedOrders) {
				if(parameter.isUsePlaceholder()) {
					if(purposeEntity.isGetSqlParameterValues()) {
						if(parameters == null) 
							parameters = new ArrayList<Object>();
						parameters.add(new InputSqlParameter(parameter.getValue(sqlParameter, alias), parameter.getDBDataType()));
					}
				}else {
					content = content.replaceAll(parameter.getConfigHolder().getPrefix() + parameter.getName() + parameter.getConfigHolder().getSuffix(), parameter.getValuePrefix() + parameter.getValue(sqlParameter, alias) + parameter.getValueSuffix());
				}
			}
		}
		this.content = content;
		this.sqlParameters = purposeEntity.isGetSqlParameters()?sqlParameterByDefinedOrders:null;
	}
	
	public ExecuteSqlNode(String finalContent, List<Object> parameters, List<SqlParameterMetadata> sqlParameters) {
		this.content = finalContent;
		this.parameters = parameters;
		this.sqlParameters = sqlParameters;
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
	public boolean existsParameters() {
		return parameters != null;
	}
	public List<SqlParameterMetadata> getSqlParameters() {
		return sqlParameters;
	}
	public boolean existsSqlParameters() {
		return sqlParameters != null;
	}
}
