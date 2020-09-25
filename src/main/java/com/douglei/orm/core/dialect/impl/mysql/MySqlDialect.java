package com.douglei.orm.core.dialect.impl.mysql;

import java.lang.reflect.InvocationTargetException;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.dialect.impl.AbstractDialect;
import com.douglei.orm.core.dialect.impl.mysql.datatype.handler.DataTypeHandlerMapping;
import com.douglei.orm.core.dialect.impl.mysql.db.features.DBFeaturesImpl;
import com.douglei.orm.core.dialect.impl.mysql.db.object.DBObjectHandlerImpl;
import com.douglei.orm.core.dialect.impl.mysql.db.sql.SqlHandlerImpl;
import com.douglei.orm.core.dialect.impl.mysql.db.sql.SqlQueryHandlerImpl;
import com.douglei.orm.core.dialect.impl.mysql.db.sql.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public final class MySqlDialect extends AbstractDialect{
	
	public MySqlDialect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		dataTypeHandlerMapping = new DataTypeHandlerMapping();
		
		feature = new DBFeaturesImpl();
		objectHandler = new DBObjectHandlerImpl();
		
		sqlHandler = new SqlHandlerImpl();
		sqlStatementHandler = new SqlStatementHandlerImpl();
		sqlQueryHandler = new SqlQueryHandlerImpl(sqlStatementHandler);
	}

	@Override
	public DialectType getType() {
		return DialectType.MYSQL;
	}
}
