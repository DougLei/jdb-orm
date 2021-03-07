package com.douglei.orm.mapping.impl.sql.executor.content.node.impl;

import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.sql.SqlNodeContainer;
import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.executor.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.executor.content.node.SqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.IncludeSqlNode;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.ExecutableSql;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class IncludeSqlNodeExecutor implements SqlNodeExecutor<IncludeSqlNode> {

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.INCLUDE;
	}

	@Override
	public ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, IncludeSqlNode currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Object sqlParameter, String previousAlias) {
		SqlContentMetadata sqlContentMetadata = sqlContentMetadataMap.get(currentSqlNode.getRefName());
		ExecutableSql executableSql = new ExecutableSql(purposeEntity, sqlContentMetadata, sqlContentMetadataMap, sqlParameter);
		return new ExecutableSqlNode(executableSql.getSql(), executableSql.getParameters(), executableSql.getParameterValues());
	}

	@Override
	public ValidateFailResult validate(IncludeSqlNode currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Map<String, List<Validator>> validatorsMap, Object sqlParameter, String previousAlias) {
		SqlContentMetadata sqlContentMetadata = sqlContentMetadataMap.get(currentSqlNode.getRefName());
		for (SqlNode sqlNode : sqlContentMetadata.getSqlNodes()) {
			SqlNodeExecutor<SqlNode> executor = SqlNodeContainer.getExecutor(sqlNode);
			if(executor.matching(sqlNode, sqlParameter, previousAlias)) {
				ValidateFailResult failResult = executor.validate(sqlNode, sqlContentMetadataMap, validatorsMap, sqlParameter, previousAlias);
				if(failResult != null)
					return failResult;
			}
		}
		return null;
	}
}
