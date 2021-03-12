package com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlQueryMetadata;

/**
 *
 * @author DougLei
 */
public class ParameterGroup extends AbstractParameter{
	private List<AbstractParameter> parameters;

	/**
	 * 添加参数
	 * @param parameter
	 * @return
	 */
	public ParameterGroup addParameter(AbstractParameter parameter) {
		if(parameters == null)
			parameters = new ArrayList<AbstractParameter>();
		parameters.add(parameter);
		return this;
	}

	@Override
	protected void assembleSQL(ExecutableQuerySql entity, SqlQueryMetadata metadata) throws QuerySqlAssembleException {
		if(parameters == null)
			return;
		
		entity.appendConditionSQLLeftParenthesis();
		for(int i=0;i<parameters.size();i++) {
			parameters.get(i).assembleSQL(entity, metadata);
			if(i < parameters.size()-1)
				entity.appendConditionSQLNext(parameters.get(i).next);
		}
		entity.appendConditionSQLRightParenthesis();
	}
}
