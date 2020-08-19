package com.douglei.orm.generated_key;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import com.douglei.tools.utils.IdentityUtil;

public class GeneratedKeysTest {
	
	@Test
	public void sqlserver() throws Exception {
//		// 获取自增主键值
//	    Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=jbpm", "sa", "root");
//	    Statement statement = conn.createStatement();
//	    statement.execute("insert into BPM_RE_PROCTYPE(code, name, create_date, update_date) values('code', 'name', getdate(), getdate())", Statement.RETURN_GENERATED_KEYS);
//	    
//	    ResultSet rs = statement.getGeneratedKeys();
//	    if(rs.next())
//	    	System.out.println(rs.getInt(1));
		
		// 获取自定义主键值
	    Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=jbpm", "sa", "root");
	    Statement statement = conn.createStatement();
	    statement.execute("insert into UUID_TABLE values('"+IdentityUtil.get32UUID()+"', 'NAME')", Statement.RETURN_GENERATED_KEYS);
	    
	    ResultSet rs = statement.getGeneratedKeys();
	    if(rs.next())
	    	System.out.println(rs.getObject(1));
	}
}
