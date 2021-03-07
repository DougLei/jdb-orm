package com.douglei.orm.mapping.impl.sql.executor.content.node;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.executor.content.node.impl.ParameterNodeExecutor;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ParameterNode;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;

/**
 * 可执行的sql node
 * @author DougLei
 */
public class ExecutableSqlNode {
	private String sql;
	private List<ParameterNode> parameters; // sql参数集合
	private List<Object> parameterValues; // 执行sql语句对应的参数值集合
	
	/**
	 * 空的ExecutableSqlNode实例
	 */
	public static final ExecutableSqlNode EMPTY_EXECUTABLE_SQL_NODE = new ExecutableSqlNode("", null, null);
	
	public ExecutableSqlNode(PurposeEntity purposeEntity, String sql, List<ParameterNode> parameters, Object sqlParameter, String previousAlias) {
		if(parameters != null) {
			for (ParameterNode parameter : parameters) {
				if(parameter.isPlaceholder()) {
					if(purposeEntity.isGetParameterValues()) {
						if(parameterValues == null) 
							parameterValues = new ArrayList<Object>();
						parameterValues.add(ParameterNodeExecutor.SINGLETON.getValue(parameter, sqlParameter, previousAlias));
					}
				}else {
					// 非占位符, 将实际值替换到变量的位置
					sql = ParameterNodeExecutor.SINGLETON.replaceSql(sql, parameter, sqlParameter, previousAlias);
				}
			}
		}
		this.sql = sql;
		this.parameters = purposeEntity.isGetParameters()?parameters:null;
	}
	
	public ExecutableSqlNode(String sql, List<ParameterNode> parameters, List<Object> parameterValues) {
		this.sql = sql;
		this.parameters = parameters;
		this.parameterValues = parameterValues;
	}

	public String getSql() {
		return sql;
	}
	public List<ParameterNode> getParameters(){
		return parameters;
	}
	public List<Object> getParameterValues() {
		return parameterValues;
	}
}