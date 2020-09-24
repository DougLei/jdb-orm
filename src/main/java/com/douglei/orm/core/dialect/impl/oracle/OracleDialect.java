package com.douglei.orm.core.dialect.impl.oracle;

import java.lang.reflect.InvocationTargetException;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.dialect.impl.AbstractDialect;
import com.douglei.orm.core.dialect.impl.oracle.datatype.handler.DataTypeHandlerMapping;
import com.douglei.orm.core.dialect.impl.oracle.db.features.DBFeaturesImpl;
import com.douglei.orm.core.dialect.impl.oracle.db.object.DBObjectHandlerImpl;
import com.douglei.orm.core.dialect.impl.oracle.db.sql.SqlHandlerImpl;
import com.douglei.orm.core.dialect.impl.oracle.db.sql.SqlStatementHandlerImpl;

/**
 * 
 * @author DougLei
 */
public final class OracleDialect extends AbstractDialect{
	
	public OracleDialect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		dataTypeHandlerMapping = new DataTypeHandlerMapping();
		
		feature = new DBFeaturesImpl();
		objectHandler = new DBObjectHandlerImpl();
		
		sqlHandler = new SqlHandlerImpl();
		sqlStatementHandler = new SqlStatementHandlerImpl();
	}

	@Override
	public DialectType getType() {
		return DialectType.ORACLE;
	}
}
