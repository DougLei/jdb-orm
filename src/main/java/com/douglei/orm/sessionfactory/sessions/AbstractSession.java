package com.douglei.orm.sessionfactory.sessions;

import java.sql.Connection;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.ConnectionEntity;
import com.douglei.orm.mapping.handler.MappingHandler;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractSession {
	protected boolean isClosed; // session是否关闭
	protected ConnectionEntity connection;
	protected Environment environment;
	protected MappingHandler mappingHandler;
	
	protected AbstractSession(ConnectionEntity connection, Environment environment) {
		this.connection = connection;
		this.environment = environment;
		this.mappingHandler = environment.getMappingHandler();
	}
	
	/**
	 * 验证session是否被关闭
	 */
	protected void validateIsClosed() {
		if(isClosed) 
			throw new SessionExecuteException(getClass().getName() + "已经关闭");
	}
	
	/**
	 * 获取数据库原生连接; 该连接使用完成后<b>禁止直接关闭</b>, 其会随着session实例的关闭而关闭
	 * <p>
	 * 通过该Connection创建的其他对象需要手动关闭, 例如Statement
	 * @return
	 */
	public final Connection getConnection() {
		validateIsClosed();
		return connection.getConnection();
	}
	
	/**
	 * 关闭session
	 */
	public abstract void close();
}
