package com.douglei.sessions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.sql.ConnectionWrapper;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractSession {
	private static final Logger logger = LoggerFactory.getLogger(AbstractSession.class);
	
	protected ConnectionWrapper connection;
	
	protected EnvironmentProperty environmentProperty;
	protected MappingWrapper mappingWrapper;
	

	
	public AbstractSession(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		this.connection = connection;
		this.environmentProperty = environmentProperty;
		this.mappingWrapper = mappingWrapper;
	}
	
	public void close() {
		if(!connection.isFinishTransaction()) {
			logger.info("当前[{}]的事物没有处理结束: commit 或 rollback, 程序默认进行 commit操作", getClass());
			connection.commit();
		}
	}
	
	/**
	 * 刷新session实例
	 */
	protected abstract void flush();
	
	public void commit() {
		connection.commit();
	}
	public void rollback() {
		connection.rollback();
	}
}
