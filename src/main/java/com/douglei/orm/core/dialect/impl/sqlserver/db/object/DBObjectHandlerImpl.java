package com.douglei.orm.core.dialect.impl.sqlserver.db.object;

import java.util.List;

import com.douglei.orm.core.dialect.db.object.DBObjectHandler;
import com.douglei.orm.core.dialect.db.object.DBObjectType;
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
	public String getQueryDBObjectIsExistsSqlStatement(DBObjectType dbObjectType, String dbObjectName, List<Object> parameters) {
		parameters.add(dbObjectName);
		switch(dbObjectType) {
			case PROCEDURE:
				parameters.add("P");
				break;
			case VIEW:
				parameters.add("V");
				break;
		}
		return "select count(1) from  sysobjects where id = object_id(?) and type = ?";
	}

	@Override
	public PrimaryKeySequence createPrimaryKeySequence(String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		return SqlServerPrimaryKeySequence.singleInstance();
	}
}
