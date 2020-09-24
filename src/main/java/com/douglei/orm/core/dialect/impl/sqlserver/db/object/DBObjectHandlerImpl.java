package com.douglei.orm.core.dialect.impl.sqlserver.db.object;

import com.douglei.orm.core.dialect.db.object.DBObjectHandler;
import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.dialect.impl.sqlserver.db.object.pk.sequence.SqlServerPrimaryKeySequence;
import com.douglei.orm.core.metadata.table.ColumnMetadata;

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
		return SqlServerPrimaryKeySequence.singleInstance();
	}
}
