package com.douglei.orm.dialect.impl.mysql.db.object;

import com.douglei.orm.dialect.db.object.DBObjectHandler;
import com.douglei.orm.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.dialect.impl.mysql.db.object.pk.sequence.MySqlPrimaryKeySequence;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;

/**
 * 
 * @author DougLei
 */
public class DBObjectHandlerImpl extends DBObjectHandler {

	@Override
	protected short nameMaxLength() {
		return 64;
	}
	
	@Override
	public PrimaryKeySequence createPrimaryKeySequence(String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return new MySqlPrimaryKeySequence(name, createSql, dropSql, tableName, primaryKeyColumn);
	}
}
