package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.executor.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.ExecutableSql;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;

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
	public SqlNodeType getType() {
		return SqlNodeType.INCLUDE;
	}
}
