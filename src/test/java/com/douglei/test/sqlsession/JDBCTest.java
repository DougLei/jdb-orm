package com.douglei.test.sqlsession;

import java.sql.DriverManager;

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
		System.out.println(DriverManager.getConnection(url, username, pwd).getMetaData().getURL());
	}
}
