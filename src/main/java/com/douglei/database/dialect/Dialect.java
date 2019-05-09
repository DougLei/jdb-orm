package com.douglei.database.dialect;

import com.douglei.database.dialect.datatype.DataTypeHandler;

/**
 * dialect处理器
 * @author DougLei
 */
public interface Dialect {
	
	/**
	 * 获取数据库编码值, 绝对唯一
	 * @return
	 */
	String getDatabaseCode();
	
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
	DataTypeHandler getDataTypeHandler(String code);
	
	/**
	 * 根据值的class, 获取对应的DataTypeHandler
	 * @param value
	 * @return
	 */
	DataTypeHandler getDataTypeHandler(Object value);
}
