package com.douglei.orm.sessionfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.ConnectionWrapper;
import com.douglei.orm.configuration.environment.datasource.TransactionIsolationLevel;
import com.douglei.orm.mapping.handler.MappingHandler;
import com.douglei.orm.sessionfactory.sessions.Session;
import com.douglei.orm.sessionfactory.sessions.SessionImpl;
import com.douglei.orm.sessionfactory.validator.DataValidator;

/**
 * 
 * @author DougLei
 */
public class SessionFactory {
	private static final Logger logger = LoggerFactory.getLogger(SessionFactory.class);
	
	private Configuration configuration;
	private Environment environment;
	private DataValidator dataValidator;
	
	public SessionFactory(Configuration configuration, Environment environment) {
		this.configuration = configuration;
		this.environment = environment;
		this.dataValidator = new DataValidator(environment.getMappingHandler());
	}
	
	/**
	 * 获取id值
	 * @return
	 */
	public String getId() {
		return configuration.getId();
	}
	
	/**
	 * 开启Session实例, 默认开启事物
	 * @return
	 */
	public Session openSession() {
		return openSession(true);
	}
	/**
	 * 开启Session实例
	 * @param isBeginTransaction 是否开启事物
	 * @return
	 */
	public Session openSession(boolean isBeginTransaction) {
		return openSession(isBeginTransaction, null);
	}
	/**
	 * 开启Session实例
	 * @param isBeginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果传入null, 则使用jdbc默认的隔离级别
	 * @return
	 */
	public Session openSession(boolean isBeginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		logger.debug("open {} 实例, 获取connection实例, 是否开启事务: {}, 事物的隔离级别: {}", SessionImpl.class, isBeginTransaction, transactionIsolationLevel);
		return new SessionImpl(getConnectionWrapper(isBeginTransaction, transactionIsolationLevel), environment);
	}
	private ConnectionWrapper getConnectionWrapper(boolean isBeginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		return environment.getDataSourceWrapper().getConnection(isBeginTransaction, transactionIsolationLevel);
	}
	
	/**
	 * 获取映射处理器
	 * @return
	 */
	public MappingHandler getMappingHandler() {
		EnvironmentContext.setProperty(environment.getEnvironmentProperty());
		return environment.getMappingHandler();
	}
	
	/**
	 * 获取数据验证器
	 * @return
	 */
	public DataValidator getDataValidator() {
		EnvironmentContext.setProperty(environment.getEnvironmentProperty());
		return dataValidator;
	}
	
	/**
	 * 销毁
	 */
	public void destroy() {
		if(configuration != null) {
			configuration.destroy();
			configuration = null;
		}
	}
}
