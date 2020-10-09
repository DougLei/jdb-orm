package com.douglei.orm.dialect.impl.mysql.object.pk.sequence;

import com.douglei.orm.dialect.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;

/**
 * mysql主键序列
 * @author DougLei
 */
public class MySqlPrimaryKeySequence extends PrimaryKeySequence{
	private static final long serialVersionUID = -1695408916315078845L;

	public MySqlPrimaryKeySequence(String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		super.createSql = "alter table "+tableName+" change column "+primaryKeyColumn.getName()+" "+primaryKeyColumn.getName()+" "+primaryKeyColumn.getDBDataType().getName()+" auto_increment";
		super.dropSql = "alter table "+tableName+" change column "+primaryKeyColumn.getName()+" "+primaryKeyColumn.getName()+" "+primaryKeyColumn.getDBDataType().getName();
	}
}
