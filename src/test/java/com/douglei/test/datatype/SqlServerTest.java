package com.douglei.test.datatype;

import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.douglei.utils.CloseUtil;

public class SqlServerTest {
	private Connection conn;
	private PreparedStatement insertPst;
	private PreparedStatement selectPst;
	private ResultSet rs;
	
	@Test
	public void stringTest() throws Exception {// varchar2, nvarchar2, char, nchar
		insertPst.setString(1, "哈哈123-(varchar2, nvarchar2, char, nchar)");
	}
	
	@Test
	public void intTest() throws Exception {// number
		insertPst.setInt(1, 78);
	}
	
	@Test
	public void floatTest() throws Exception {// number
		insertPst.setDouble(1, 22.22);
	}
	
	@Test
	public void dateTest() throws Exception {// date
		insertPst.setTimestamp(1, new Timestamp(new Date().getTime()));
	}
	
	@Test
	public void clobTest() throws Exception {// clob
		String clob = "     哈哈哈哈哈哈哈啊fjsalkjflksajf     ";
		insertPst.setCharacterStream(1, new StringReader(clob), clob.length());
	}
	
	@Test
	public void blobTest() throws Exception {// blob
		String clob = "     哈哈哈哈哈哈哈啊fjsalkjflksajf     ";
		byte[] b = clob.getBytes();
		insertPst.setBinaryStream(1, new ByteArrayInputStream(b), b.length);
	}
	
	@Before
	public void before() throws Exception {
		String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url =  "jdbc:sqlserver://localhost:1433;DatabaseName=douglei";
		String username = "sa";
		String pwd = "root";
		Class.forName(className);
		conn = DriverManager.getConnection(url, username, pwd);
		insertPst = conn.prepareStatement("insert into test(T) values(?)");
		selectPst = conn.prepareStatement("select T from test");
		rs = selectPst.executeQuery();
		System.err.println("读取的列类型值为 = " + rs.getMetaData().getColumnType(1));
		System.err.println("读取的列类型名为 = " + rs.getMetaData().getColumnTypeName(1));
		System.err.println("读取的列类型精度为 = " + rs.getMetaData().getScale(1));
		if(rs.next()) {
			System.err.println("读取的列值为 = "+rs.getString(1));
//			System.err.println(rs.getDate(1));
//			InputStream in = rs.getBinaryStream(1);
//			if(in == null) {
//				return;
//			}
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			byte[] b = new byte[1024];
//			int length = 0;
//			
//			while((length=in.read(b)) != -1) {
//				out.write(b, 0, length);
//			}
//			System.out.println(Arrays.toString(out.toByteArray()));
//			System.out.println(Arrays.toString("     哈哈哈哈哈哈哈啊fjsalkjflksajf     ".getBytes()));
			
			Reader reader = rs.getCharacterStream(1);
			if(reader == null) {
				System.err.println(1);
				return;
			}
			
			StringWriter writer = null;
			writer = new StringWriter();
			int length;
			char[] ch = new char[512];
			while((length = reader.read(ch)) != -1) {
				writer.write(ch, 0, length);
			}
			System.err.println("读取的列值为 = "+writer.toString());
		}
	}
	@After
	public void after() throws Exception {
		System.err.println("\n执行insert影响的行数 = "+insertPst.executeUpdate());
		CloseUtil.closeDBConn(rs, insertPst, selectPst, conn);
	}
}
