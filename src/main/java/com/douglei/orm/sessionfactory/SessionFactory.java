package com.douglei.orm.sessionfactory;

import com.douglei.orm.core.dialect.TransactionIsolationLevel;
import com.douglei.orm.sessionfactory.data.validator.DataValidatorProcessor;
import com.douglei.orm.sessionfactory.mapping.MappingProcessor;
import com.douglei.orm.sessionfactory.sessions.Session;

/**
 * 
 * @author DougLei
 */
public interface SessionFactory {
	
	/**
	 * 
	 * @return
	 */
	String getId();
	
	/**
	 * <pre>
	 * 	开启Session实例
	 * 	默认开启事物
	 * </pre>
	 * @return
	 */
	Session openSession();
	
	/**
	 * <pre>
	 * 	开启Session实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @return
	 */
	Session openSession(boolean beginTransaction);
	
	/**
	 * <pre>
	 * 	开启Session实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果传入null, 则使用jdbc默认的隔离级别
	 * @return
	 */
	Session openSession(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel);
	
	/**
	 * 获取映射处理器
	 * @return
	 */
	default MappingProcessor getMappingProcessor() {
		return null;
	}
	
	/**
	 * 获取数据验证处理器
	 * @return
	 */
	default DataValidatorProcessor getDataValidatorProcessor() {
		return null;
	}
	
	/**
	 * 销毁
	 */
	void destroy();
}
