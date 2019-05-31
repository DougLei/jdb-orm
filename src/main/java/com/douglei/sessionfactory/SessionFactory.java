package com.douglei.sessionfactory;

import com.douglei.configuration.environment.mapping.MappingType;
import com.douglei.database.dialect.TransactionIsolationLevel;
import com.douglei.sessions.session.sql.SQLSession;
import com.douglei.sessions.session.table.TableSession;
import com.douglei.sessions.sqlsession.SqlSession;

/**
 * 创建session的factory接口
 * @author DougLei
 */
public interface SessionFactory {
	
	/**
	 * <pre>
	 * 	动态添加映射
	 * 	如果是表映射, 则顺便create表
	 * </pre>
	 * @param mappingType
	 * @param mappingConfigurationContent
	 */
	void dynamicAddMapping(MappingType mappingType, String mappingConfigurationContent);
	/**
	 * <pre>
	 * 	动态覆盖映射
	 * 	只对映射操作, 不对实体操作
	 * </pre>
	 * @param mappingType
	 * @param mappingConfigurationContent
	 */
	void dynamicCoverMapping(MappingType mappingType, String mappingConfigurationContent);
	/**
	 * <pre>
	 * 	动态删除映射
	 * 	如果是表映射, 则顺便drop表
	 * </pre>
	 * @param mappingCode
	 */
	void dynamicRemoveMapping(String mappingCode);
	
	/**
	 * <pre>
	 * 	开启TableSession实例
	 * 	默认开启事物
	 * </pre>
	 * @return
	 */
	TableSession openTableSession();
	
	/**
	 * <pre>
	 * 	开启TableSession实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @return
	 */
	TableSession openTableSession(boolean beginTransaction);
	
	/**
	 * <pre>
	 * 	开启TableSession实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果传入null, 则使用jdbc默认的隔离级别
	 * @return
	 */
	TableSession openTableSession(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel);
	
	/**
	 * <pre>
	 * 	开启SQLSession实例
	 * 	默认开启事物
	 * </pre>
	 * @return
	 */
	SQLSession openSQLSession();
	
	/**
	 * <pre>
	 * 	开启SQLSession实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @return
	 */
	SQLSession openSQLSession(boolean beginTransaction);
	
	/**
	 * <pre>
	 * 	开启SQLSession实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果传入null, 则使用jdbc默认的隔离级别
	 * @return
	 */
	SQLSession openSQLSession(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel);
	
	/**
	 * <pre>
	 * 	开启sql session实例
	 * 	默认开启事物
	 * </pre>
	 * @return
	 */
	SqlSession openSqlSession();
	
	/**
	 * <pre>
	 * 	开启sql session实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @return
	 */
	SqlSession openSqlSession(boolean beginTransaction);
	
	/**
	 * <pre>
	 * 	开启sql session实例
	 * </pre>
	 * @param beginTransaction 是否开启事物
	 * @param transactionIsolationLevel 事物隔离级别, 如果传入null, 则使用jdbc默认的隔离级别
	 * @return
	 */
	SqlSession openSqlSession(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel);
}
