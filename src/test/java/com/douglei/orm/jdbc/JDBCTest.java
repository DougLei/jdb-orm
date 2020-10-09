package com.douglei.orm.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import com.douglei.tools.utils.IdentityUtil;

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
		PreparedStatement pst = connection.prepareStatement("insert into SYS_USER2(id,age) values(?, ?)");
		
		pst.setString(1, IdentityUtil.get32UUID());
		pst.setObject(2, null);
		pst.executeUpdate();
		connection.commit();
		
	}
	
	@Test
	public void mssql() throws Exception {
		String url =  "jdbc:sqlserver://localhost:1433;DatabaseName=douglei";
		String username = "sa";
		String pwd = "root";
		
		Connection connection = DriverManager.getConnection(url, username, pwd);
		ResultSet rs = connection.createStatement().executeQuery("select text_, numeric_ from number_table");
		while(rs.next()) {
			System.out.println(rs.getString(1).getClass());
			System.out.println();
			System.out.println(rs.getObject(2).getClass());
		}
	}
}
