package com.douglei.test.sqlsession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import org.junit.Test;


public class JDBCTest {
	
	@Test
	public void mysql() throws Exception {
		String className = "com.mysql.cj.jdbc.Driver";
		String url =  "jdbc:mysql://localhost:3306/douglei?characterEncoding=utf8&useSSL=true";
		String username = "root";
		String pwd = "root";
		
		Class.forName(className);
		System.out.println(DriverManager.getConnection(url, username, pwd).getMetaData().getURL());
	}
	
	@Test
	public void oracle() throws Exception {
		String className = "oracle.jdbc.driver.OracleDriver";
		String url =  "jdbc:oracle:thin:@localhost:1521:ORCL";
		String username = "douglei";
		String pwd = "root";
		
		Class.forName(className);
		System.out.println(DriverManager.getConnection(url, username, pwd).getMetaData().getURL());
	}
	
	@Test
	public void mssql() throws Exception {
		String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url =  "jdbc:sqlserver://localhost:1433;DatabaseName=douglei";
		String username = "sa";
		String pwd = "root";
		
		Class.forName(className);
		Connection conn = DriverManager.getConnection(url, username, pwd);
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from sys_user");
		
		
		ResultSetMetaData metadata = rs.getMetaData();
		int count = metadata.getColumnCount();
		for (int i = 0; i <count; i++) {
			System.out.println(metadata.getColumnType(i+1));
			System.out.println(metadata.getColumnTypeName(i+1));
			System.out.println("---------------------------------------");
		}
		
	}
}
