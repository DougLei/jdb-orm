package com.douglei.orm.core.dialect.impl.oracle.db.object.pk.sequence;

import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;

/**
 * oracle主键序列
 * @author DougLei
 */
public class OraclePrimaryKeySequence extends PrimaryKeySequence{

	public OraclePrimaryKeySequence(String name, String tableName, String createSql, String dropSql) {
		super(name, tableName, createSql, dropSql);
	}

	@Override
	protected String defaultCreateSql() {
		return "create sequence " + getName();
	}

	@Override
	protected String defaultDropSql() {
		return "drop sequence " + getName();
	}

	@Override
	public String getNextvalSql() {
		return getName() + ".nextval";
	}
	
	@Override
	public String getCurrvalSql() {
		return getName() + ".currval";
	}
}
