package com.douglei.orm.mapping.handler.object;

import java.sql.SQLException;

import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.orm.mapping.metadata.AbstractMetadata;

/**
 * 对象处理器
 * @author DougLei
 */
public abstract class ObjectHandler<M, D> {
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
	 * 创建对象, 根据createMode决定如何同步
	 * @param createParameter
	 * @param exMetadata 之前的元数据, 如果当前创建的对象为新对象, 则该参数为null
	 * @throws Exception 
	 */
	public abstract void create(M createParameter, M exMetadata) throws Exception;

	/**
	 * 删除对象
	 * @param deleteParameter
	 * @param exMetadata 之前的元数据, 如果当前创建的对象为新对象, 则该参数为null
	 * @throws SQLException
	 */
	public abstract void delete(D deleteParameter, M exMetadata) throws SQLException;
}
