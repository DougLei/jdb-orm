package com.douglei.database.dialect;

import com.douglei.database.dialect.datatype.DataTypeHandler;

/**
 * dialect处理器
 * @author DougLei
 */
public interface Dialect {
	
	/**
	 * 组装成分页查询的sql语句
	 * @param pageNum 
	 * @param pageSize 
	 * @param sql
	 * @return
	 */
	String installPageQuerySql(int pageNum, int pageSize, String sql);
	
	/**
	 * 根据code值, 获取对应的DataTypeHandler
	 * @param code
	 * @return
	 */
	DataTypeHandler getDataTypeHandlerByCode(String code);

	/**
	 * 根据value的classType, 获取对应的DataTypeHandler
	 * @param value
	 * @return
	 */
	DataTypeHandler getDataTypeHandlerByValueClassType(Object value);

	/**
	 * 根据数据库的ResultSetMetadata的ColumnType, 获取对应的DataTypeHandler
	 * @param columnType
	 * @return
	 */
	DataTypeHandler getDataTypeHandlerByDatabaseColumnType(int columnType);
}
