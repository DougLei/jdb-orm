package com.douglei.orm.core.dialect.impl.oracle.db.object;

import java.util.List;

import com.douglei.orm.core.dialect.db.object.DBObjectHandler;
import com.douglei.orm.core.dialect.db.object.DBObjectType;
import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.dialect.impl.oracle.db.object.pk.sequence.OraclePrimaryKeySequence;

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
	public String getQueryDBObjectIsExistsSqlStatement(DBObjectType dbObjectType, String dbObjectName, List<Object> parameters) {
		parameters.add(dbObjectName);
		parameters.add(dbObjectType.name());
		return "select count(1) from user_objects where object_name = ? and object_type = ?";
	}

	@Override
	public PrimaryKeySequence createPrimaryKeySequence(String tableName, String primaryKeyColumnName, String name, String createSql, String dropSql) {
		// TODO 创建oracle主键序列对象
		return new OraclePrimaryKeySequence();
	}
}
