package com.douglei.orm.core.dialect.db.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.douglei.tools.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
public class SqlQueryConnection {
	private Connection connection;
	private List<Statement> stList;
	private Map<String, PreparedStatement> pstMap;
	
	public SqlQueryConnection(Connection connection) {
		this.connection = connection;
	}

	public Statement getStatement() throws SQLException {
		Statement st = connection.createStatement();
		if(stList == null)
			stList = new ArrayList<Statement>();
		stList.add(st);
		return st;
	}
	
	public PreparedStatement getPreparedStatement(String sql) throws SQLException {
		if(pstMap == null)
			pstMap = new HashMap<String, PreparedStatement>(4);
		
		String signature = DigestUtils.md5Hex(sql);
		PreparedStatement pst = null;
		if(!pstMap.isEmpty())
			pst = pstMap.get(signature);
		
		if(pst == null) {
			pst = connection.prepareStatement(sql);
			pstMap.put(signature, pst);
		}
		return pst;
	}

	public void close() {
		if(stList != null)
			for(Statement st : stList)
				CloseUtil.closeDBConn(st);
		if(pstMap != null)
			for(PreparedStatement pst : pstMap.values())
				CloseUtil.closeDBConn(pst);
	}
}
