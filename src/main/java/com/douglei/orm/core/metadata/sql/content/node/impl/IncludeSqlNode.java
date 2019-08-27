package com.douglei.orm.core.metadata.sql.content.node.impl;

import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.core.metadata.sql.SqlContentMetadata;
import com.douglei.orm.core.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNodeType;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.ExecuteSql;

/**
 * 
 * @author DougLei
 */
public class IncludeSqlNode implements SqlNode {
	private SqlContentMetadata content;
	
	public IncludeSqlNode(SqlContentMetadata content) {
		this.content = content;
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.INCLUDE;
	}

	@Override
	public ExecuteSqlNode getExecuteSqlNode(Object sqlParameter, String sqlParameterNamePrefix) {
		if(content.isMatchingDialectType(EnvironmentContext.getEnvironmentProperty().getDialect().getType())) {
			ExecuteSql executeSql = new ExecuteSql(content, sqlParameter);
			return new ExecuteSqlNode(executeSql.getContent(), executeSql.getParameters());
		}
		return new ExecuteSqlNode("", null);
	}
}
