package com.douglei.orm.core.metadata.sql.content.node.impl;

import java.util.List;

import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.core.metadata.sql.SqlContentMetadata;
import com.douglei.orm.core.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNodeType;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.ExecuteSql;

/**
 * 
 * @author DougLei
 */
public class IncludeSqlNode implements SqlNode {
	private static final long serialVersionUID = 4680613324336879116L;
	private SqlContentMetadata content;
	private List<SqlNode> rootSqlNodes;
	
	public IncludeSqlNode(SqlContentMetadata content) {
		this.content = content;
		this.rootSqlNodes = content.getRootSqlNodes();
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.INCLUDE;
	}

	@Override
	public ExecuteSqlNode getExecuteSqlNode(Object sqlParameter, String sqlParameterNamePrefix) {
		if(content.isMatchingDialectType(EnvironmentContext.getDialect().getType())) {
			ExecuteSql executeSql = new ExecuteSql(content, sqlParameter);
			return new ExecuteSqlNode(executeSql.getContent(), executeSql.getParameters());
		}
		return ExecuteSqlNode.emptyExecuteSqlNode();
	}

	@Override
	public ValidationResult validateParameter(Object sqlParameter, String sqlParameterNamePrefix) {
		if(content.isMatchingDialectType(EnvironmentContext.getDialect().getType())) {
			ValidationResult result = null;
			for (SqlNode sqlNode : rootSqlNodes) {
				if(sqlNode.matching(sqlParameter, sqlParameterNamePrefix)) {
					if((result = sqlNode.validateParameter(sqlParameter)) != null) {
						return result;
					}
				}
			}
		}
		return null;
	}
}
