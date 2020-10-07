package com.douglei.orm.dialect;

import com.douglei.orm.dialect.datatype.DataTypeContainer;
import com.douglei.orm.dialect.object.DBObjectHandler;
import com.douglei.orm.dialect.sql.SqlQueryHandler;
import com.douglei.orm.dialect.sql.SqlStatementHandler;

/**
 * 方言
 * @author DougLei
 */
public interface Dialect {
	
	/**
	 * 
	 * @return
	 */
	DialectType getType();
	
	/**
	 * 
	 * @param type
	 */
	void setType(DialectType type);
	
	
	/**
	 * 获取数据类型容器
	 * @return
	 */
	DataTypeContainer getDataTypeContainer();
	
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
}
