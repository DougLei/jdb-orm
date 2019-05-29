package com.douglei.database.dialect.impl.sqlserver;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.dialect.impl.AbstractDialect;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.SqlServerDataTypeHandlerMapping;
import com.douglei.database.dialect.impl.sqlserver.db.features.FeaturesHolderImpl;
import com.douglei.database.dialect.impl.sqlserver.db.objectname.DBObjectNameHandlerImpl;
import com.douglei.database.dialect.impl.sqlserver.db.sql.SqlHandlerImpl;

/**
 * 
 * @author DougLei
 */
public final class SqlServerDialect extends AbstractDialect{
	public static final DialectType DIALECT_TYPE = DialectType.SQLSERVER;
	
	@Override
	public DialectType getType() {
		return DIALECT_TYPE;
	}
	
	@Override
	protected void initialize() {
		super.sqlHandler = new SqlHandlerImpl();
		super.dbObjectNameHandler = new DBObjectNameHandlerImpl();
		super.procedureHandler = new FeaturesHolderImpl();
		super.dataTypeHandlerMapping = new SqlServerDataTypeHandlerMapping();
	}
}
