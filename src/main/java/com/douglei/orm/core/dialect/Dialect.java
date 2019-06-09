package com.douglei.orm.core.dialect;

import com.douglei.orm.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.core.dialect.db.features.FeaturesHolder;
import com.douglei.orm.core.dialect.db.objectname.DBObjectNameHandler;
import com.douglei.orm.core.dialect.db.sql.SqlHandler;
import com.douglei.orm.core.dialect.db.table.TableSqlStatementHandler;

/**
 * dialect处理器
 * @author DougLei
 */
public interface Dialect {
	
	AbstractDataTypeHandlerMapping getDataTypeHandlerMapping();
	
	SqlHandler getSqlHandler();
	DBObjectNameHandler getDBObjectNameHandler();
	FeaturesHolder getFeaturesHolder();
	TableSqlStatementHandler getTableSqlStatementHandler();
	
	DialectType getType();
}
