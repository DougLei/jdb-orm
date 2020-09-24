package com.douglei.orm.core.mapping.struct;

import java.sql.SQLException;

import com.douglei.orm.core.mapping.serialization.SerializationHandler;
import com.douglei.orm.core.metadata.Metadata;

/**
 * 结构处理器
 * @author DougLei
 */
public class StructHandler {
	protected SerializationHandler serializationHandler = SerializationHandler.getSingleton();
	protected DBConnection connection;
	
	public StructHandler(DBConnection connection) {
		this.connection = connection;
	}
	
	/**
	 * 执行sql语句
	 * @param sql
	 * @throws SQLException 
	 */
	protected void executeSql(String sql) throws SQLException {
		connection.executeSql(sql);
	}

	public void create(Metadata metadata) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Metadata metadata) {
		// TODO Auto-generated method stub
		
	}
	
}
