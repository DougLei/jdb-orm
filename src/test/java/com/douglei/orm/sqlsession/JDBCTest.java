package com.douglei.orm.sqlsession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.Test;


public class JDBCTest {
	
	@Test
	public void mysql() throws Exception {
		String url =  "jdbc:mysql://localhost:3306/douglei?characterEncoding=utf8&useSSL=true";
		String username = "root";
		String pwd = "root";
		
//		System.out.println(DriverManager.getConnection(url, username, pwd).getMetaData().getURL());
//		
		Connection conn = DriverManager.getConnection(url, username, pwd);
		PreparedStatement pst = conn.prepareStatement("drop procedure test");
//		pst.setString(1, "test.");
		pst.executeUpdate();
		
//		System.out.println(conn.getMetaData().getDatabaseMajorVersion());
//		System.out.println(conn.getMetaData().getDatabaseMinorVersion());
//		System.out.println(conn.getMetaData().getDatabaseProductVersion());
		
//		PreparedStatement pst = conn.prepareCall("delete sys_user where id = ? "
//				+ "delete sys_user where id = ?");
//		pst.setString(1, "1");
//		pst.setString(2, "2");
//		pst.executeUpdate();
	}
	
	@Test
	public void oracle() throws Exception {
		String className = "oracle.jdbc.driver.OracleDriver";
		String url =  "jdbc:oracle:thin:@localhost:1521:ORCL";
		String username = "douglei";
		String pwd = "root";
		
		Class.forName(className);
//		System.out.println(DriverManager.getConnection(url, username, pwd).getMetaData().getURL());
		
		Connection conn = DriverManager.getConnection(url, username, pwd);
		System.out.println(conn.getMetaData().getDatabaseMajorVersion());
		System.out.println(conn.getMetaData().getDatabaseMinorVersion());
		System.out.println(conn.getMetaData().getDatabaseProductVersion());
		
//		conn.setAutoCommit(true);
//		PreparedStatement pst = conn.prepareCall("insert into sys_user(id) values(?)");
//		pst.setString(1, "isAutoCommit");
//		System.out.println(pst.executeUpdate());
//		
//		
//		conn.setAutoCommit(false);
//		pst.setString(1, "notAutoCommit");
//		System.out.println(pst.executeUpdate());
		
//		conn.commit();
	}
	
	@Test
	public void mssql() throws Exception {
		String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url =  "jdbc:sqlserver://localhost:1433;DatabaseName=douglei";
		String username = "sa";
		String pwd = "root";
		
		Class.forName(className);
		Connection conn = DriverManager.getConnection(url, username, pwd);
		
		System.out.println(conn.getMetaData().getDatabaseMajorVersion());
		System.out.println(conn.getMetaData().getDatabaseMinorVersion());
		System.out.println(conn.getMetaData().getDatabaseProductVersion());
		
		
//		Statement st = conn.createStatement();
//		ResultSet rs = st.executeQuery("select * from sys_user");
//		
//		
//		ResultSetMetaData metadata = rs.getMetaData();
//		int count = metadata.getColumnCount();
//		for (int i = 0; i <count; i++) {
//			System.out.println(metadata.getColumnType(i+1));
//			System.out.println(metadata.getColumnTypeName(i+1));
//			System.out.println("---------------------------------------");
//		}
		
		
//		PreparedStatement pst = conn.prepareCall("delete sys_user where id = ? "
//				+ "delete sys_user where id = ?");
//		pst.setString(1, "1");
//		pst.setString(2, "2");
//		pst.executeUpdate();
	}
}
