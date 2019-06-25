package com.douglei.orm.configuration.environment;

import java.sql.Connection;
import java.sql.SQLException;

import com.douglei.tools.utils.CloseUtil;

/**
 * 数据库元数据
 * @author DougLei
 */
public class DatabaseMetadata {
	private String JDBCUrl; 
	private int databaseMajorVersion;
	
	public DatabaseMetadata(Connection connection) throws SQLException {
		java.sql.DatabaseMetaData dm = connection.getMetaData();
		try {
			JDBCUrl = dm.getURL();
			databaseMajorVersion = dm.getDatabaseMajorVersion();
		} finally {
			CloseUtil.closeDBConn(connection);
		}
	}
	
	public String getJDBCUrl() {
		return JDBCUrl;
	}
	public int getDatabaseMajorVersion() {
		return databaseMajorVersion;
	}

	@Override
	public String toString() {
		return "DatabaseMetadata [JDBCUrl=" + JDBCUrl + ", databaseMajorVersion=" + databaseMajorVersion + "]";
	} 
}
