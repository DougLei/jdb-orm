package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeType;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.ExecutableSql;

/**
 * 
 * @author DougLei
 */
public class IncludeSqlNode extends AbstractNestingNode {
	
	private String refName; 
	public IncludeSqlNode(String refName) {
		this.refName = refName;
	}

	public String getRefName() {
		return refName;
	}

	@Override
	public ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String previousAlias) {
		ExecutableSql executableSql = new ExecutableSql(purposeEntity, content, sqlParameter);
		return new ExecutableSqlNode(executableSql.getContent(), executableSql.getParameters(), executableSql.getParameterValues());
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.INCLUDE;
	}
}
