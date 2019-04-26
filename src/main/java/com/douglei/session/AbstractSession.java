package com.douglei.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.database.sql.TransactionNotFinishException;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractSession {
	private static final Logger logger = LoggerFactory.getLogger(AbstractSession.class);
	
	protected ConnectionWrapper connection;
	
	protected EnvironmentProperty environmentProperty;
	protected MappingWrapper mappingWrapper;
	protected boolean enableSessionCache;// 是否启用session缓存
	
	public AbstractSession(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		this.connection = connection;
		this.environmentProperty = environmentProperty;
		this.mappingWrapper = mappingWrapper;
		this.enableSessionCache = environmentProperty.getEnableSessionCache();
	}
	
	public void close() {
		if(!connection.isFinishTransaction()) {
			logger.error("当前[{}]的事物没有处理结束: commit 或 rollback", getClass());
			throw new TransactionNotFinishException("当前["+getClass()+"]的事物没有处理结束: commit 或 rollback");
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
