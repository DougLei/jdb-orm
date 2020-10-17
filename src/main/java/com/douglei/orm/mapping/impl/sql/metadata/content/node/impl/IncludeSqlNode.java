package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecuteSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.ExecuteSql;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class IncludeSqlNode implements SqlNode {
	private static final long serialVersionUID = -6945918274123214610L;
	
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
	public ExecuteSqlNode getExecuteSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String sqlParameterNamePrefix) {
		ExecuteSql executeSql = new ExecuteSql(purposeEntity, content, sqlParameter);
		return new ExecuteSqlNode(executeSql.getContent(), executeSql.getParameters(), executeSql.getSqlParameters());
	}

	@Override
	public ValidationResult validateParameter(Object sqlParameter, String sqlParameterNamePrefix) {
		ValidationResult result = null;
		for (SqlNode sqlNode : rootSqlNodes) {
			if(sqlNode.matching(sqlParameter, sqlParameterNamePrefix)) {
				if((result = sqlNode.validateParameter(sqlParameter)) != null) {
					return result;
				}
			}
		}
		return null;
	}
}
