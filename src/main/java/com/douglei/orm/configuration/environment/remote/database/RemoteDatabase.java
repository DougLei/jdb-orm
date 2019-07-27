package com.douglei.orm.configuration.environment.remote.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.SelfProcessing;
import com.douglei.tools.utils.CloseUtil;
import com.douglei.tools.utils.ExceptionUtil;

/**
 * 远程数据库
 * @author DougLei
 */
public abstract class RemoteDatabase implements SelfProcessing{
	private static final Logger logger = LoggerFactory.getLogger(RemoteDatabase.class);
	
	protected String username;
	protected String password;
	protected String url;
	
	protected boolean destroy;
	
	protected List<String> createSql;
	protected List<String> dropSql;
	
	/**
	 * 添加create sql
	 * @param sql
	 */
	protected void addCreateSql(String sql) {
		if(createSql == null) {
			createSql = new ArrayList<String>(5);
		}
		createSql.add(sql);
	}
	
	/**
	 * 添加drop sql
	 * @param dropSql
	 */
	protected void addDropSql(String sql) {
		if(dropSql == null) {
			dropSql = new ArrayList<String>(5);
		}
		dropSql.add(sql);
	}
	
	private void execute(List<String> sqls, RemoteDatabaseOperateType remoteDatabaseOperateType) {
		boolean executedAnySql = false;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			statement = connection.createStatement();
			
			for (String sql : sqls) {
				logger.debug("正向: {}", sql);
				statement.execute(sql);
				executedAnySql = true;
			}
		} catch (SQLException e) {
			logger.error("正向: 执行{}数据库的sql语句时出现异常 {}", remoteDatabaseOperateType.name(), ExceptionUtil.getExceptionDetailMessage(e));
			rollback(statement, remoteDatabaseOperateType, executedAnySql);
			throw new RemoteDatabaseOperateException("执行"+remoteDatabaseOperateType.name() + "数据库的sql语句时出现异常", e);
		} finally {
			CloseUtil.closeDBConn(statement, connection);
		}
	}
	
	private void rollback(Statement statement, RemoteDatabaseOperateType remoteDatabaseOperateType, boolean executedAnySql) {
		if(executedAnySql) { // 执行过了任何一条或多条sql, 才有回滚的意义
			RemoteDatabaseOperateType rollbackRemoteDatabaseOperateType = (remoteDatabaseOperateType==RemoteDatabaseOperateType.CREATE) ? RemoteDatabaseOperateType.DROP : RemoteDatabaseOperateType.CREATE;
			List<String> sqls = (rollbackRemoteDatabaseOperateType==RemoteDatabaseOperateType.CREATE) ? createSql:dropSql;
			for (String sql : sqls) {
				logger.debug("逆向: {}", sql);
				try {
					statement.execute(sql);
				} catch (SQLException e) {
					logger.error("逆向(回滚): 执行{}数据库的sql语句时出现异常 {}", rollbackRemoteDatabaseOperateType.name(), ExceptionUtil.getExceptionDetailMessage(e));
				}
			}
		}
	}
	
	public void executeCreate() {
		execute(createSql, RemoteDatabaseOperateType.CREATE);
	}
	
	private void executeDrop() {
		execute(dropSql, RemoteDatabaseOperateType.DROP);
	}

	@Override
	public void destroy() throws DestroyException {
		if(destroy) {
			executeDrop();
		}
		createSql.clear();
		dropSql.clear();
	}
}
