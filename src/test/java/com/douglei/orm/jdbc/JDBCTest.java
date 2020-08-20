package com.douglei.orm.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

public class JDBCTest {
	
	@Test
	public void test() throws Exception {
		Connection connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=douglei", "sa", "root");
		
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("select 1.1 as bbbbb from LOG_SQL_1");
		
		System.out.println(rs.getMetaData().getColumnName(1));
		System.out.println(rs.getMetaData().getColumnType(1));
		System.out.println(rs.getMetaData().getColumnTypeName(1));
		
		
		
		
//		st.executeUpdate("insert into sys_user(id, name) values (pkseq_sys_user.nextval, '哈哈')");
		
//		PreparedStatement pst = connection.prepareStatement("insert into log_operation(id, is_success) values (newid(), ?)");
//		pst.setShort(1, (short)-1);
//		pst.executeUpdate();
//		connection.commit();
		
		
		
		
	}
	
	@Test
	public void mysql() throws Exception {
		String url =  "jdbc:mysql://localhost:3306/douglei?characterEncoding=utf8&useSSL=true";
		String username = "root";
		String pwd = "root";
		
//		System.out.println(DriverManager.getConnection(url, username, pwd).getMetaData().getURL());
//		
		Connection connection = DriverManager.getConnection(url, username, pwd);
//		PreparedStatement pst = conn.prepareStatement("drop procedure test");
//		pst.setString(1, "test.");
//		pst.executeUpdate();
		
		
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("select 1.1 as bbbbb from log_operation");
		
		System.out.println(rs.getMetaData().getColumnName(1));
		System.out.println(rs.getMetaData().getColumnType(1));
		System.out.println(rs.getMetaData().getColumnTypeName(1));
		
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
		String url =  "jdbc:oracle:thin:@localhost:1521:ORCL";
		String username = "douglei";
		String pwd = "root";
		
		Connection connection = DriverManager.getConnection(url, username, pwd);
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("select test_seq.nextval from dual");
		rs.next();
		System.out.println("next value is " + rs.getInt(1));
		rs = st.executeQuery("select test_seq.currval from dual");
		rs.next();
		System.out.println("current value is " + rs.getInt(1));
		System.out.println(rs.getObject(1).getClass());
	}
	
	@Test
	public void mssql() throws Exception {
		String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url =  "jdbc:sqlserver://localhost:1433;DatabaseName=douglei";
		String username = "sa";
		String pwd = "root";
		
		Class.forName(className);
		Connection connection = DriverManager.getConnection(url, username, pwd);
//		Statement statement = connection.createStatement();
//		ResultSet rs = statement.executeQuery("select * from sys_user");
//		while(rs.next()) {
//			System.out.println(rs.getString(1) + "\t" + rs.getString(2));
//		}
//		
//		connection = statement.getConnection();
//		statement = connection.createStatement();
//		rs = statement.executeQuery("select * from sys_user");
//		while(rs.next()) {
//			System.out.println(rs.getString(1) + "\t" + rs.getString(2));
//		}
		
		PreparedStatement statement = connection.prepareStatement("select * from sys_user");
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString(1) + "\t" + rs.getString(2));
		}
		
		Connection c = statement.getConnection();
		System.out.println(c == connection);
		statement = connection.prepareStatement("select * from sys_user");
		rs = statement.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString(1) + "\t" + rs.getString(2));
		}
		
	}
}
