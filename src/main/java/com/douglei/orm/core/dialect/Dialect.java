package com.douglei.orm.core.dialect;

import com.douglei.orm.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.core.dialect.db.feature.DBFeature;
import com.douglei.orm.core.dialect.db.object.DBObjectHandler;
import com.douglei.orm.core.dialect.db.sql.SqlQueryHandler;
import com.douglei.orm.core.dialect.db.sql.SqlStatementHandler;

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
