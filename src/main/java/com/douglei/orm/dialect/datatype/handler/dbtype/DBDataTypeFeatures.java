package com.douglei.orm.dialect.datatype.handler.dbtype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 数据库的DataType特性
 * @author DougLei
 */
public interface DBDataTypeFeatures {
	
	/**
	 * 获取默认对应的数据库数据类型
	 * @return
	 */
	public DBDataType getDBDataType();
	
	/**
	 * 获取数据库type名称
	 * @return
	 */
	default String getTypeName() {
		return getDBDataType().getTypeName();
	}
	
	/**
	 * 获取数据库type值
	 * @see java.sql.Types
	 * @return
	 */
	default int getSqlType() {
		return getDBDataType().getSqlType();
	}
	
	/**
	 * 是否是字符类型
	 * @return
	 */
	default boolean isCharacterType() {
		return false;
	}
}