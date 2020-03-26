package com.douglei.orm.sqlsession;

import java.sql.Connection;
import java.sql.DriverManager;
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
//		String className = "oracle.jdbc.driver.OracleDriver";
		String url =  "jdbc:oracle:thin:@localhost:1521:ORCL";
		String username = "douglei";
		String pwd = "root";
		
//		Class.forName(className);
//		System.out.println(DriverManager.getConnection(url, username, pwd).getMetaData().getURL());
		
		Connection connection = DriverManager.getConnection(url, username, pwd);
//		System.out.println(conn.getMetaData().getDatabaseMajorVersion());
//		System.out.println(conn.getMetaData().getDatabaseMinorVersion());
//		System.out.println(conn.getMetaData().getDatabaseProductVersion());
		
		
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("select 1.1 as bbbbb from I18N_MESSAGE_1");
		
		System.out.println(rs.getMetaData().getColumnName(1));
		System.out.println(rs.getMetaData().getColumnType(1));
		System.out.println(rs.getMetaData().getColumnTypeName(1));
		
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
		
//		System.out.println(conn.getMetaData().getDatabaseMajorVersion());
//		System.out.println(conn.getMetaData().getDatabaseMinorVersion());
//		System.out.println(conn.getMetaData().getDatabaseProductVersion());
	
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select name, age from [User] where name = '啊哈'");
		if(rs.next()) {
			System.out.println(rs.getObject("age").getClass());
		}
		
			
		
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
