package com.douglei.orm.sessionfactory;

import java.util.List;

import com.douglei.orm.core.dialect.TransactionIsolationLevel;
import com.douglei.orm.core.dialect.db.table.handler.TableSqlStatementHandler;
import com.douglei.orm.sessions.Session;

/**
 * 创建session的factory接口
 * @author DougLei
 */
public interface SessionFactory {
	
	/**
	 * <pre>
	 * 	动态添加映射, 如果存在则覆盖
	 * 	如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * </pre>
	 * @param entity
	 */
	void dynamicAddMapping(DynamicMapping entity);
	/**
	 * <pre>
	 * 	动态批量添加映射, 如果存在则覆盖
	 * 	如果是表映射, 则顺便根据createMode的配置, 进行相应的操作
	 * </pre>
	 * @param entities
	 */
	void dynamicBatchAddMapping(List<DynamicMapping> entities);
	
	/**
	 * <pre>
	 * 	动态覆盖映射, 如果不存在添加
	 * 	<b>只对映射操作</b>
	 * 	<b>不对实体进行任何操作, 主要是不会对表进行相关的操作</b>
	 * </pre>
	 * @param entity
	 */
	void dynamicCoverMapping(DynamicMapping entity);
	/**
	 * <pre>
	 * 	动态覆盖映射, 如果不存在添加
	 * 	<b>只对映射操作</b>
	 * 	<b>不对实体进行任何操作, 主要是不会对表进行相关的操作</b>
	 * </pre>
	 * @param entities
	 */
	void dynamicBatchCoverMapping(List<DynamicMapping> entities);
	
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
	
	/**
	 * 销毁
	 */
	void destroy();
	
	/**
	 * 
	 * @return
	 */
	String getId();
}
