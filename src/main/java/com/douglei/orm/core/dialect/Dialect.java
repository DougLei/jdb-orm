package com.douglei.orm.core.dialect;

import com.douglei.orm.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.core.dialect.db.features.DBFeatures;
import com.douglei.orm.core.dialect.db.object.DBObjectHandler;
import com.douglei.orm.core.dialect.db.sql.SqlHandler;
import com.douglei.orm.core.dialect.db.table.TableSqlStatementHandler;

/**
 * dialect处理器
 * @author DougLei
 */
public interface Dialect {
	
	AbstractDataTypeHandlerMapping getDataTypeHandlerMapping();
	
	SqlHandler getSqlHandler();
	DBObjectHandler getDBObjectHandler();
	DBFeatures getDBFeatures();
	TableSqlStatementHandler getTableSqlStatementHandler();
	
	DialectType getType();
}
