package com.douglei.database.dialect;

import com.douglei.database.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.db.database.DatabaseSqlStatementHandler;
import com.douglei.database.dialect.db.features.FeaturesHolder;
import com.douglei.database.dialect.db.objectname.DBObjectNameHandler;
import com.douglei.database.dialect.db.sql.SqlHandler;
import com.douglei.database.dialect.db.table.TableHandler;
import com.douglei.database.dialect.db.table.TableSqlStatementHandler;

/**
 * dialect处理器
 * @author DougLei
 */
public interface Dialect {
	
	AbstractDataTypeHandlerMapping getDataTypeHandlerMapping();
	
	TableHandler getTableHandler();
	SqlHandler getSqlHandler();
	DBObjectNameHandler getDBObjectNameHandler();
	FeaturesHolder getFeaturesHolder();
	DatabaseSqlStatementHandler getDatabaseSqlStatementHandler();
	TableSqlStatementHandler getTableSqlStatementHandler();
	
	DialectType getType();
}
