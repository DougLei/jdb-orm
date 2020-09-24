package com.douglei.orm.core.dialect.impl.sqlserver;

import java.lang.reflect.InvocationTargetException;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.dialect.impl.AbstractDialect;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.DataTypeHandlerMapping;
import com.douglei.orm.core.dialect.impl.sqlserver.db.features.DBFeaturesImpl;
import com.douglei.orm.core.dialect.impl.sqlserver.db.object.DBObjectHandlerImpl;
import com.douglei.orm.core.dialect.impl.sqlserver.db.sql.SqlHandlerImpl;
import com.douglei.orm.core.dialect.impl.sqlserver.db.sql.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public final class SqlServerDialect extends AbstractDialect{
	
	public SqlServerDialect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		dataTypeHandlerMapping = new DataTypeHandlerMapping();
		
		feature = new DBFeaturesImpl();
		objectHandler = new DBObjectHandlerImpl();
		
		sqlHandler = new SqlHandlerImpl();
		sqlStatementHandler = new SqlStatementHandlerImpl();
	}

	@Override
	public DialectType getType() {
		return DialectType.SQLSERVER;
	}
}
