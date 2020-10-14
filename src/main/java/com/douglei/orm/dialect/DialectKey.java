package com.douglei.orm.dialect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.tools.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
public class DialectKey {
	private static final Logger logger = LoggerFactory.getLogger(DialectKey.class);
	
	private String name;
	private int databaseMajorVersion;
	
	public DialectKey(Connection connection) throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		try {
			name = extractDialectName(databaseMetaData.getURL());
			databaseMajorVersion = databaseMetaData.getDatabaseMajorVersion();
		} finally {
			CloseUtil.closeDBConn(connection);
		}
	}
	private String extractDialectName(String JDBCUrl) {
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
			throw new ArithmeticException("JDBCUrl=" + JDBCUrl + ", 无法从中截取到对应的dialect数据, 第一个冒号的下标="+a+", 第二个冒号的下标="+b);
		
		String dialectName = JDBCUrl.substring(a, b-1).toUpperCase();
		logger.debug("从 JDBCUrl= {}中, 提取的dialect名为 {}", JDBCUrl, dialectName);
		return dialectName;
	}

	public String getName() {
		return name;
	}
	public int getDatabaseMajorVersion() {
		return databaseMajorVersion;
	}
	
	@Override
	public String toString() {
		return "[name=" + name + ", databaseMajorVersion=" + databaseMajorVersion + "]";
	}
}
