package com.douglei.orm.core.dialect.impl.oracle.db.object;

import com.douglei.orm.core.dialect.db.object.DBObjectHandler;
import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.dialect.impl.oracle.db.object.pk.sequence.OraclePrimaryKeySequence;
import com.douglei.orm.core.metadata.table.ColumnMetadata;

/**
 * 
 * @author DougLei
 */
public class DBObjectHandlerImpl extends DBObjectHandler {

	@Override
	protected short nameMaxLength() {
		return 30;
	}

	@Override
	public PrimaryKeySequence createPrimaryKeySequence(String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return new OraclePrimaryKeySequence(name, createSql, dropSql, tableName, primaryKeyColumn);
	}
}
