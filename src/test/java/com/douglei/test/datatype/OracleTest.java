package com.douglei.test.datatype;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.utils.CloseUtil;

public class OracleTest {
	private Connection conn;
	private PreparedStatement insertPst;
	private PreparedStatement selectPst;
	private ResultSet rs;
	
	@Test
	public void stringTest() throws Exception {// varchar2, nvarchar2, char, nchar
		insertPst.setString(1, "哈哈123-(varchar2, nvarchar2, char, nchar)");
		if(rs.next()) {
			System.err.println("读取的列值为 = "+rs.getNString(1));
		}
	}
	
	@Test
	public void intTest() throws Exception {// number
		
	}
	
	@Test
	public void floatTest() throws Exception {// number
		
	}
	
	@Test
	public void dateTest() throws Exception {// date
		
	}
	
	@Test
	public void clobTest() throws Exception {// clob
		
	}
	
	@Test
	public void blobTest() throws Exception {// blob
		
	}
	
	@Before
	public void before() throws Exception {
		String className = "oracle.jdbc.driver.OracleDriver";
		String url =  "jdbc:oracle:thin:@localhost:1521:ORCL";
		String username = "douglei";
		String pwd = "root";
		Class.forName(className);
		conn = DriverManager.getConnection(url, username, pwd);
		insertPst = conn.prepareStatement("insert into test(T) values(?)");
		selectPst = conn.prepareStatement("select T from test");
		rs = selectPst.executeQuery();
		System.err.println("读取的列类型值为 = " + rs.getMetaData().getColumnType(1));
		System.err.println("读取的列类型名为 = " + rs.getMetaData().getColumnTypeName(1));
	}
	@After
	public void after() throws Exception {
		System.err.println("\n执行insert影响的行数 = "+insertPst.executeUpdate());
		CloseUtil.closeDBConn(rs, insertPst, selectPst, conn);
	}
}
