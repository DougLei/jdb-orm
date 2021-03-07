package com.douglei.orm.mapping.impl.sql.executor.content.node.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.sql.SqlNodeContainer;
import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.executor.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.executor.content.node.SqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ParameterNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.TrimSqlNode;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNodeExecutor extends AbstractNestingNodeExecutor<TrimSqlNode> implements SqlNodeExecutor<TrimSqlNode> {

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TRIM;
	}

	@Override
	public ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, TrimSqlNode currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Object sqlParameter, String previousAlias) {
		StringBuilder sqlBuilder = new StringBuilder();
		List<ParameterNode> parameters = null;
		List<Object> parameterValues = null;
		
		for (SqlNode sqlNode : currentSqlNode.getSqlNodes()) {
			SqlNodeExecutor<SqlNode> executor = SqlNodeContainer.getExecutor(sqlNode);
			if(executor.matching(sqlNode, sqlParameter, previousAlias)) {
				ExecutableSqlNode executableSqlNode = executor.getExecutableSqlNode(purposeEntity, sqlNode, sqlContentMetadataMap, sqlParameter);
				if(executableSqlNode.getParameters() != null) {
					if(parameters == null)
						parameters = new ArrayList<ParameterNode>();
					parameters.addAll(executableSqlNode.getParameters());
				}
				if(executableSqlNode.getParameterValues() != null) {
					if(parameterValues == null) 
						parameterValues = new ArrayList<Object>();
					parameterValues.addAll(executableSqlNode.getParameterValues());
				}
				sqlBuilder.append(executableSqlNode.getSql()).append(' ');
			}
		}
		
		String sql = sqlBuilder.toString();
		int sqlLength = sql.length();
		if(sqlLength > 0) {
			if(currentSqlNode.getPrefixOverride() != null) {
				for (String po : currentSqlNode.getPrefixOverride()) {
					if(po.equalsIgnoreCase(sql.substring(0, po.length()))) {
						sql = sql.substring(po.length());
						break;
					}
				}
			}
			if(currentSqlNode.getSuffixOverride() != null) {
				sqlLength--;
				for (String so : currentSqlNode.getSuffixOverride()) {
					if(so.equalsIgnoreCase(sql.substring(sqlLength-so.length(), sqlLength))) {
						sql = sql.substring(0, sqlLength-so.length());
						break;
					}
				}
			}
			sql = currentSqlNode.getPrefix() + sql + currentSqlNode.getSuffix();
		}
		return new ExecutableSqlNode(sql, parameters, parameterValues);
	}
}
