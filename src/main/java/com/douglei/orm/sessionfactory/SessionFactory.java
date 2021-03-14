package com.douglei.orm.sessionfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.OrmException;
import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.EnvironmentContext;
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
	
	private String id;
	private Environment environment;
	private DataValidator dataValidator;
	
	public SessionFactory(String id, Environment environment) {
		this.id = id;
		this.environment = environment;
		this.dataValidator = new DataValidator(environment.getMappingHandler());
	}
	
	/**
	 * 获取id
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 开启Session实例, 默认开启事物
	 * @return
	 */
	public Session openSession() {
		return openSession(true, TransactionIsolationLevel.DEFAULT);
	}
	/**
	 * 开启Session实例
	 * @param isBeginTransaction 是否开启事物
	 * @return
	 */
	public Session openSession(boolean isBeginTransaction) {
		return openSession(isBeginTransaction, TransactionIsolationLevel.DEFAULT);
	}
	/**
	 * 开启Session实例
	 * @param isBeginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别
	 * @return
	 */
	public Session openSession(boolean isBeginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		logger.debug("openSession, 获取connection实例, 是否开启事务: {}, 事物的隔离级别: {}", isBeginTransaction, transactionIsolationLevel);
		EnvironmentContext.setEnvironment(environment);
		return new SessionImpl(environment.getDataSource().getConnection(isBeginTransaction, transactionIsolationLevel), environment);
	}
	
	/**
	 * 获取映射处理器
	 * @return
	 */
	public MappingHandler getMappingHandler() {
		EnvironmentContext.setEnvironment(environment);
		return environment.getMappingHandler();
	}
	
	/**
	 * 获取数据验证器
	 * @return
	 */
	public DataValidator getDataValidator() {
		EnvironmentContext.setEnvironment(environment);
		return dataValidator;
	}
	
	/**
	 * 销毁
	 */
	public void destroy() {
		if(environment != null) {
			try {
				environment.destroy();
				environment = null;
			} catch (Exception e) {
				throw new OrmException("销毁SessionFactory实例时出现异常", e);
			}
		}
	}
}
