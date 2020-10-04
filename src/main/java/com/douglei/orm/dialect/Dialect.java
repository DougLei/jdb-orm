package com.douglei.orm.dialect;

import com.douglei.orm.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.dialect.db.feature.DBFeature;
import com.douglei.orm.dialect.db.object.DBObjectHandler;
import com.douglei.orm.dialect.db.sql.SqlQueryHandler;
import com.douglei.orm.dialect.db.sql.SqlStatementHandler;

/**
 * dialect处理器
 * @author DougLei
 */
public interface Dialect {
	
	AbstractDataTypeHandlerMapping getDataTypeHandlerMapping();
	
	DBFeature getFeature();
	
	DBObjectHandler getObjectHandler();

	SqlStatementHandler getSqlStatementHandler();
	SqlQueryHandler getSqlQueryHandler();
	
	DialectType getType();
}
