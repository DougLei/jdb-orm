package com.douglei.orm.mapping.impl.sql.executor.content.node.impl;

import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.sql.SqlNodeContainer;
import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.executor.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.executor.content.node.SqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.SwitchSqlNode;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class SwitchSqlNodeExecutor implements SqlNodeExecutor<SwitchSqlNode> {

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.SWITCH;
	}

	@Override
	public ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, SwitchSqlNode currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Object sqlParameter, String previousAlias) {
		for (SqlNode sqlNode : currentSqlNode.getSqlNodes()) {
			SqlNodeExecutor<SqlNode> executor = SqlNodeContainer.getExecutor(sqlNode);
			if(executor.matching(sqlNode, sqlParameter, previousAlias)) 
				return executor.getExecutableSqlNode(purposeEntity, sqlNode, sqlContentMetadataMap, sqlParameter);
		}
		return ExecutableSqlNode.EMPTY_EXECUTABLE_SQL_NODE;
	}

	@Override
	public ValidateFailResult validate(SwitchSqlNode currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Map<String, List<Validator>> validatorsMap, Object sqlParameter, String previousAlias) {
		for (SqlNode sqlNode : currentSqlNode.getSqlNodes()) {
			SqlNodeExecutor<SqlNode> executor = SqlNodeContainer.getExecutor(sqlNode);
			if(executor.matching(sqlNode, sqlParameter, previousAlias)) 
				return executor.validate(sqlNode, sqlContentMetadataMap, validatorsMap, sqlParameter, previousAlias);
		}
		return null;
	}
}
