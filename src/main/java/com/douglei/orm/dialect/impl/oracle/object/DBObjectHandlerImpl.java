package com.douglei.orm.dialect.impl.oracle.object;

import com.douglei.orm.dialect.impl.oracle.object.pk.sequence.OraclePrimaryKeySequence;
import com.douglei.orm.dialect.object.DBObjectHandler;
import com.douglei.orm.dialect.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;

/**
 * 
 * @author DougLei
 */
public class DBObjectHandlerImpl extends DBObjectHandler {

	@Override
	public boolean supportProcedureDirectlyReturnResultSet() {
		return false;
	}
	
	@Override
	protected short nameMaxLength() {
		return 30;
	}

	@Override
	public PrimaryKeySequence createPrimaryKeySequence(String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return new OraclePrimaryKeySequence(name, createSql, dropSql, tableName, primaryKeyColumn);
	}
}
