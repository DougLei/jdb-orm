package com.douglei.database.dialect.impl.oracle;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.dialect.impl.AbstractDialect;
import com.douglei.database.dialect.impl.oracle.datatype.handler.OracleDataTypeHandlerMapping;
import com.douglei.database.dialect.impl.oracle.db.features.FeaturesHolderImpl;
import com.douglei.database.dialect.impl.oracle.db.objectname.DBObjectNameHandlerImpl;
import com.douglei.database.dialect.impl.oracle.db.sql.SqlHandlerImpl;

/**
 * 
 * @author DougLei
 */
public final class OracleDialect extends AbstractDialect{
	public static final DialectType DIALECT_TYPE = DialectType.ORACLE;
	
	@Override
	public DialectType getType() {
		return DIALECT_TYPE;
	}
	
	@Override
	protected void initialize() {
		super.sqlHandler = new SqlHandlerImpl();
		super.dbObjectNameHandler = new DBObjectNameHandlerImpl();
		super.procedureHandler = new FeaturesHolderImpl();
		super.dataTypeHandlerMapping = new OracleDataTypeHandlerMapping();
	}
}
