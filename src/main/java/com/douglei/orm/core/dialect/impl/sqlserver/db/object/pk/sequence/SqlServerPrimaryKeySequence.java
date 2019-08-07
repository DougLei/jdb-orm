package com.douglei.orm.core.dialect.impl.sqlserver.db.object.pk.sequence;

import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.metadata.table.ColumnMetadata;

/**
 * sqlserver主键序列
 * @author DougLei
 */
public class SqlServerPrimaryKeySequence extends PrimaryKeySequence{
	private static final long serialVersionUID = 1654980884354188374L;
	private static final SqlServerPrimaryKeySequence instance = new SqlServerPrimaryKeySequence();
	public static PrimaryKeySequence singleInstance() {
		return instance;
	}
	
	private SqlServerPrimaryKeySequence() {
		super(false, null, null, null, null, null);
	}
	
	@Override
	protected String processCreateSql(String createSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return null;
	}

	@Override
	protected String processDropSql(String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return null;
	}

	@Override
	public String getNextvalSql() {
		return null;
	}
	
	@Override
	public String getCurrvalSql() {
		return null;
	}
}
