package com.douglei.orm.core.dialect.impl.mysql.db.object.pk.sequence;

import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.metadata.table.ColumnMetadata;

/**
 * oracle主键序列
 * @author DougLei
 */
public class MySqlPrimaryKeySequence extends PrimaryKeySequence{

	public MySqlPrimaryKeySequence(String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		super(name, createSql, dropSql, tableName, primaryKeyColumn);
	}
	
	@Override
	protected String processCreateSql(String createSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return "alter table "+tableName+" change column "+primaryKeyColumn.getName()+" "+primaryKeyColumn.getName()+" "+primaryKeyColumn.getDBDataType().getTypeName()+" auto_increment";
	}

	@Override
	protected String processDropSql(String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return "alter table "+tableName+" change column "+primaryKeyColumn.getName()+" "+primaryKeyColumn.getName()+" "+primaryKeyColumn.getDBDataType().getTypeName();
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
