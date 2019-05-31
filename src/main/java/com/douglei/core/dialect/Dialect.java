package com.douglei.core.dialect;

import com.douglei.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.core.dialect.db.database.DatabaseSqlStatementHandler;
import com.douglei.core.dialect.db.features.FeaturesHolder;
import com.douglei.core.dialect.db.objectname.DBObjectNameHandler;
import com.douglei.core.dialect.db.sql.SqlHandler;
import com.douglei.core.dialect.db.table.TableSqlStatementHandler;

/**
 * dialect处理器
 * @author DougLei
 */
public interface Dialect {
	
	AbstractDataTypeHandlerMapping getDataTypeHandlerMapping();
	
	SqlHandler getSqlHandler();
	DBObjectNameHandler getDBObjectNameHandler();
	FeaturesHolder getFeaturesHolder();
	DatabaseSqlStatementHandler getDatabaseSqlStatementHandler();
	TableSqlStatementHandler getTableSqlStatementHandler();
	
	DialectType getType();
}
