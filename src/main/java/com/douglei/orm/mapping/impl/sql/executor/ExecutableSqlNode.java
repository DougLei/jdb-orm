package com.douglei.orm.mapping.impl.sql.executor;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.SqlParameterNode;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;
import com.douglei.orm.sql.statement.entity.InputSqlParameter;

/**
 * 可执行的sql node
 * @author DougLei
 */
public class ExecutableSqlNode {
	private static final ExecutableSqlNode EMPTY_EXECUTABLE_SQL_NODE = new ExecutableSqlNode("", null, null);
	private String content;
	private List<SqlParameterNode> parameters; // sql参数集合
	private List<Object> parameterValues; // 执行sql语句对应的参数值集合
	
	public ExecutableSqlNode(PurposeEntity purposeEntity, String content, List<SqlParameterNode> sqlParametersByDefinedOrder, Object sqlParameter, String previousAlias) {
		if(sqlParametersByDefinedOrder != null) {
			for (SqlParameterNode parameter : sqlParametersByDefinedOrder) {
				if(parameter.isPlaceholder()) {
					if(purposeEntity.isGetParameterValues()) {
						if(parameterValues == null) 
							parameterValues = new ArrayList<Object>();
						parameterValues.add(new InputSqlParameter(parameter.getValue(sqlParameter, previousAlias), parameter.getDBDataType()));
					}
				}else {
					// 非占位符, 将实际值替换到变量的位置
					content = content.replaceAll(parameter.getConfigHolder().getPrefix() + parameter.getName() + parameter.getConfigHolder().getSuffix(), parameter.getPrefix() + parameter.getValue(sqlParameter, previousAlias) + parameter.getSuffix());
				}
			}
		}
		this.content = content;
		this.parameters = purposeEntity.isGetParameters()?sqlParametersByDefinedOrder:null;
	}
	
	public ExecutableSqlNode(String finalContent, List<SqlParameterNode> parameters, List<Object> parameterValues) {
		this.content = finalContent;
		this.parameters = parameters;
		this.parameterValues = parameterValues;
	}

	/**
	 * 获取一个空的ExecutableSqlNode实例
	 * @return
	 */
	public static ExecutableSqlNode emptyExecutableSqlNode() {
		return EMPTY_EXECUTABLE_SQL_NODE;
	}
	
	public String getContent() {
		return content;
	}
	public List<SqlParameterNode> getParameters(){
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
