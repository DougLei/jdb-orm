package com.douglei.orm.dialect.impl.sqlserver.db.object.pk.sequence;

import com.douglei.orm.dialect.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;

/**
 * sqlserver主键序列
 * @author DougLei
 */
public class SqlServerPrimaryKeySequence extends PrimaryKeySequence{
	private static final long serialVersionUID = 7767671473668198045L;
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
	public boolean executeSqlStatement() {
		return false;
	}
}
