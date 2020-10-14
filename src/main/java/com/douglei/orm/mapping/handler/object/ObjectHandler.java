package com.douglei.orm.mapping.handler.object;

import java.sql.SQLException;

import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.orm.mapping.handler.serialization.SerializationHandler;
import com.douglei.orm.mapping.metadata.AbstractMetadata;

/**
 * 对象处理器
 * @author DougLei
 */
public abstract class ObjectHandler<C, D> {
	protected SerializationHandler serializationHandler = SerializationHandler.getSingleton();
	
	protected DBConnection connection;
	protected SqlStatementHandler sqlStatementHandler;
	
	public ObjectHandler(DBConnection connection) {
		this.connection = connection;
		this.sqlStatementHandler = connection.getSqlStatementHandler();
	}
	
	/**
	 * 验证名字是否存在, 被其他对象使用
	 * @param metadata
	 * @throws SQLException 
	 */
	protected void validateNameExists(AbstractMetadata metadata) throws SQLException {
		if(connection.nameExists(metadata.getName())) 
			throw new IllegalArgumentException("数据库中已经存在名为"+metadata.getName()+"的对象");
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