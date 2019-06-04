package com.douglei.sessionfactory;

import java.util.List;

import com.douglei.core.dialect.TransactionIsolationLevel;
import com.douglei.core.dialect.db.table.TableSqlStatementHandler;
import com.douglei.sessions.Session;

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
	 * @param entity
	 */
	void dynamicAddMapping(DynamicMapping entity);
	/**
	 * <pre>
	 * 	动态覆盖映射
	 * 	只对映射操作, 不对实体操作
	 * </pre>
	 * @param entity
	 */
	void dynamicCoverMapping(DynamicMapping entity);
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
	 * 	动态批量添加映射
	 * 	如果是表映射, 则顺便create表
	 * </pre>
	 * @param entities
	 */
	void dynamicBatchAddMapping(List<DynamicMapping> entities);
	/**
	 * <pre>
	 * 	动态批量覆盖映射
	 * 	只对映射操作, 不对实体操作
	 * </pre>
	 * @param entities
	 */
	void dynamicBatchCoverMapping(List<DynamicMapping> entities);
	/**
	 * <pre>
	 * 	动态批量删除映射
	 * 	如果是表映射, 则顺便drop表
	 * </pre>
	 * @param mappingCodes
	 */
	void dynamicBatchRemoveMapping(List<String> mappingCodes);
	
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
	 * 获取表sql语句处理器
	 * @return
	 */
	TableSqlStatementHandler getTableSqlStatementHandler();
	
	void destroy();
	String getId();
}
