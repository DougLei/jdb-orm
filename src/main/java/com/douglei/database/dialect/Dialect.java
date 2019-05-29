package com.douglei.database.dialect;

import com.douglei.database.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.db.objectname.DBObjectNameHandler;
import com.douglei.database.dialect.db.procedure.ProcedureHandler;
import com.douglei.database.dialect.db.sql.SqlHandler;
import com.douglei.database.dialect.db.table.TableHandler;

/**
 * dialect处理器
 * @author DougLei
 */
public interface Dialect {
	
	DialectType getType();
	TableHandler getTableHandler();
	SqlHandler getSqlHandler();
	DBObjectNameHandler getDBObjectNameHandler();
	ProcedureHandler getProcedureHandler();
	AbstractDataTypeHandlerMapping getDataTypeHandlerMapping();
}
