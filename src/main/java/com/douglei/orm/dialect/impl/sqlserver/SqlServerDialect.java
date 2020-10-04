package com.douglei.orm.dialect.impl.sqlserver;

import java.lang.reflect.InvocationTargetException;

import com.douglei.orm.dialect.DialectType;
import com.douglei.orm.dialect.impl.AbstractDialect;
import com.douglei.orm.dialect.impl.sqlserver.datatype.handler.DataTypeHandlerMapping;
import com.douglei.orm.dialect.impl.sqlserver.db.features.DBFeaturesImpl;
import com.douglei.orm.dialect.impl.sqlserver.db.object.DBObjectHandlerImpl;
import com.douglei.orm.dialect.impl.sqlserver.db.sql.SqlQueryHandlerImpl;
import com.douglei.orm.dialect.impl.sqlserver.db.sql.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public final class SqlServerDialect extends AbstractDialect{
	
	public SqlServerDialect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		dataTypeHandlerMapping = new DataTypeHandlerMapping();
		
		feature = new DBFeaturesImpl();
		objectHandler = new DBObjectHandlerImpl();
		
		sqlStatementHandler = new SqlStatementHandlerImpl();
		sqlQueryHandler = new SqlQueryHandlerImpl(sqlStatementHandler);
	}

	@Override
	public DialectType getType() {
		return DialectType.SQLSERVER;
	}
}
