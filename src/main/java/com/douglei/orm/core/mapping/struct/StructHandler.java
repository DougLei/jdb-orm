package com.douglei.orm.core.mapping.struct;

import java.sql.SQLException;

import com.douglei.orm.core.dialect.db.sql.SqlStatementHandler;
import com.douglei.orm.core.mapping.serialization.SerializationHandler;

/**
 * 结构处理器
 * @author DougLei
 */
public abstract class StructHandler<C, D> {
	protected SerializationHandler serializationHandler = SerializationHandler.getSingleton();
	
	protected DBConnection connection;
	protected SqlStatementHandler sqlStatementHandler;
	
	public StructHandler(DBConnection connection) {
		this.connection = connection;
		this.sqlStatementHandler = connection.getSqlStatementHandler();
	}
	
	/**
	 * 创建, 根据createMode决定如何同步
	 * @param createParameter
	 * @throws Exception 
	 */
	public abstract void create(C createParameter) throws Exception;

	/**
	 * 删除
	 * @param deleteParameter
	 * @throws SQLException
	 */
	public abstract void delete(D deleteParameter) throws SQLException;
}
