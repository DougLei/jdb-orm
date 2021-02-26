package com.douglei.orm.dialect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.OrmException;

/**
 * 
 * @author DougLei
 */
class DatabaseEntity {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseEntity.class);
	private String name; // 数据库名
	private int databaseMajorVersion; // 数据库主版本
	
	public DatabaseEntity(Connection connection) throws SQLException {
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			this.name = extractDatabaseName(databaseMetaData.getURL());
			this.databaseMajorVersion = databaseMetaData.getDatabaseMajorVersion();
			logger.debug("从Connection中, 提取的数据库名为: {}, 数据库主版本为: {}", name, databaseMajorVersion);
		} finally {
			connection.close();
		}
	}
	
	// 从jdbc的url中提取出数据库名
	private String extractDatabaseName(String JDBCUrl) {
		int i=0, a=0, b=0;
		while(a==0) {
			if(JDBCUrl.charAt(i++) == ':') 
				a = i;
		}
		while(b==0) {
			if(JDBCUrl.charAt(i++) == ':') 
				b = i;
		}
		if(b-1 <= a) 
			throw new OrmException("JDBCUrl=" + JDBCUrl + ", 无法从中截取到对应的数据库名称");
		return JDBCUrl.substring(a, b-1).toUpperCase();
	}

	/**
	 * 获取数据库名
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 获取数据库主版本
	 * @return
	 */
	public int getDatabaseMajorVersion() {
		return databaseMajorVersion;
	}
	
	@Override
	public String toString() {
		return "[name=" + name + ", databaseMajorVersion=" + databaseMajorVersion + "]";
	}
}
