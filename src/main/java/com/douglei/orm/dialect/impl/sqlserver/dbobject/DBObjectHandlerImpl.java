package com.douglei.orm.dialect.impl.sqlserver.dbobject;

import com.douglei.orm.dialect.dbobject.DBObjectHandler;
import com.douglei.orm.dialect.dbobject.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.dialect.impl.sqlserver.dbobject.pk.sequence.SqlServerPrimaryKeySequence;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;

/**
 * 
 * @author DougLei
 */
public class DBObjectHandlerImpl extends DBObjectHandler {

	@Override
	protected short nameMaxLength() {
		return 128;
	}

	@Override
	public PrimaryKeySequence createPrimaryKeySequence(String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return SqlServerPrimaryKeySequence.getSingleton();
	}
}
