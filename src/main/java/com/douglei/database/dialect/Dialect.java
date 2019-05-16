package com.douglei.database.dialect;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.sql.SqlHandler;

/**
 * dialect处理器
 * @author DougLei
 */
public interface Dialect {
	
	DialectType getType();
	/**
	 * 
	 * @return
	 */
	SqlHandler getSqlHandler();
	
	/**
	 * 
	 * @return
	 */
	AbstractDataTypeHandlerMapping getDataTypeHandlerMapping();
}
