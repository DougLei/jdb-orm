package com.douglei.orm.reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

public class DBObjectContentReader {
	private String viewName = "MV";
	private String procedureName = "MP";
	
	@Test
	public void sqlserverReadViewContent() throws Exception {
		Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=douglei", "sa", "root");
		PreparedStatement ps = conn.prepareStatement("select b.definition from sysobjects a left join sys.sql_modules b on a.id=b.object_id where a.type='V' and a.name=?");
		ps.setString(1, viewName);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
			System.out.println(rs.getString(1));
	}
	@Test
	public void sqlserverReadProcedureContent() throws Exception {
		Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=douglei", "sa", "root");
		PreparedStatement ps = conn.prepareStatement("select b.definition from sysobjects a left join sys.sql_modules b on a.id=b.object_id where a.type='P' and a.name=?");
		ps.setString(1, procedureName);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
			System.out.println(rs.getString(1));
	}
	
	
	
	@Test
	public void oracleReadViewContent() throws Exception {
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "douglei", "root");
		PreparedStatement ps = conn.prepareStatement("select text_length, text from user_views where view_name=?");
		ps.setString(1, viewName);
		ResultSet rs = ps.executeQuery();
		
		StringBuilder view = null;
		if(rs.next()) {
			view = new StringBuilder(rs.getInt(1) + 16 + viewName.length());
			view.append("create view ").append(viewName).append(" as ");
			view.append(rs.getString(2));
		}
		System.out.println(view);
	}
	@Test
	public void oracleReadProcedureContent() throws Exception {
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "douglei", "root");
		PreparedStatement ps = conn.prepareStatement("select text from user_source where type='PROCEDURE' and name=? order by line");
		ps.setString(1, procedureName);
		ResultSet rs = ps.executeQuery();
		StringBuilder procedure = new StringBuilder(4000);
		
		procedure.append("create ");
		
		while(rs.next())
			procedure.append(rs.getString(1));
		System.out.println(procedure);
	}
	
	
	@Test
	public void mysqlReadViewContent() throws Exception {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/douglei?characterEncoding=utf8&useSSL=true", "root", "root");
		PreparedStatement ps = conn.prepareStatement("select view_definition from information_schema.VIEWS where table_schema = (select database()) and table_name = ? ");
		ps.setString(1, viewName);
		ResultSet rs = ps.executeQuery();
		
		StringBuilder view = new StringBuilder(16 + viewName.length());
		view.append("create view ").append(viewName).append(" as ");
		if(rs.next()) {
			view.append(rs.getString(1));
		}
		System.out.println(view);
	}
	@Test
	public void mysqlReadProcedureContent() throws Exception {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/douglei?characterEncoding=utf8&useSSL=true", "root", "root");
		Statement ps = conn.createStatement();
		ResultSet rs = ps.executeQuery("show create procedure " + procedureName);
		String script = null;
		if(rs.next())
			script = rs.getString(3);
		
		script = "create " + script.substring(script.indexOf("PROCEDURE"));
		System.out.println(script);
	}
}
