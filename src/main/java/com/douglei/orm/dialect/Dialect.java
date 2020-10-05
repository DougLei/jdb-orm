package com.douglei.orm.dialect;

import com.douglei.orm.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.dialect.db.feature.DBFeature;
import com.douglei.orm.dialect.db.object.DBObjectHandler;
import com.douglei.orm.dialect.db.sql.SqlQueryHandler;
import com.douglei.orm.dialect.db.sql.SqlStatementHandler;

/**
 * 方言
 * @author DougLei
 */
public interface Dialect {
	
	AbstractDataTypeHandlerMapping getDataTypeHandlerMapping();
	
	/**
	 * 获取数据库特性
	 * @return
	 */
	DBFeature getFeature();
	
	/**
	 * 获取数据库对象处理器
	 * @return
	 */
	DBObjectHandler getObjectHandler();

	/**
	 * 获取sql语句处理器
	 * @return
	 */
	SqlStatementHandler getSqlStatementHandler();
	/**
	 * 获取sql查询处理器
	 * @return
	 */
	SqlQueryHandler getSqlQueryHandler();
	
	DialectType getType();
}
