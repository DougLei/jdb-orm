package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecuteSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeType;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.ExecuteSql;

/**
 * 
 * @author DougLei
 */
public class IncludeSqlNode extends AbstractNestingNode {
	private static final long serialVersionUID = 7032235437420354006L;
	
	private SqlContentMetadata content;
	public IncludeSqlNode(SqlContentMetadata content) {
		this.content = content;
		this.sqlNodes = content.getSqlNodes();
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String previousAlias) {
		ExecuteSql executeSql = new ExecuteSql(purposeEntity, content, sqlParameter);
		return new ExecuteSqlNode(executeSql.getContent(), executeSql.getParameters(), executeSql.getParameterValues());
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.INCLUDE;
	}
}
