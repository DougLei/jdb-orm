package com.douglei.configuration.environment.mapping.sql;

import com.douglei.database.metadata.sql.SqlContentType;

/**
 * 当前系统进行sql mapping配置解析时, SqlContentType的持有者
 * @author DougLei
 */
public class LocalSqlMappingConfigurationSqlContentTypeHolder {
	private static final ThreadLocal<SqlContentType> SQL_CONTENT_TYPE = new ThreadLocal<SqlContentType>();
	
	public static void setCurrentSqlContentType(SqlContentType sqlContentType) {
		SQL_CONTENT_TYPE.set(sqlContentType);
	}
	
	public static SqlContentType getCurrentSqlContentType() {
		return SQL_CONTENT_TYPE.get();
	}
	
	public static boolean isProcedure() {
		return SQL_CONTENT_TYPE.get() == SqlContentType.PROCEDURE;
	}
}