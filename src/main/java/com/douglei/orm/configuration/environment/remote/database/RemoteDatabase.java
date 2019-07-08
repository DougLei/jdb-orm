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
	
	private Connection connection;
	private Statement statement;
	private boolean executeAnySql;
	
	private void execute(List<String> sqls, RemoteDatabaseOperateType remoteDatabaseOperateType, boolean throwException) {
		String sql_ = null;
		try {
			if(connection == null) {
				connection = DriverManager.getConnection(url, username, password);
			}
			if(statement == null) {
				statement = connection.createStatement();
			}
			
			for (String sql : sqls) {
				sql_ = sql;
				logger.debug("正向: {}", sql);
				statement.execute(sql);
				executeAnySql = true;
			}
		} catch (SQLException e) {
			logger.error("{} 执行{}数据库的sql语句 [{}] 时出现异常 {}", throwException?"":"逆向: ", remoteDatabaseOperateType.name(), sql_, ExceptionUtil.getExceptionDetailMessage(e));
			rollback(remoteDatabaseOperateType);
			
			if(throwException) {
				throw new RemoteDatabaseOperateException("执行"+remoteDatabaseOperateType.name() + "数据库的sql语句 ["+sql_+"] 时出现异常", e);
			}
		} finally {
			if(throwException) {
				CloseUtil.closeDBConn(statement, connection);
			}
		}
	}
	
	private void rollback(RemoteDatabaseOperateType remoteDatabaseOperateType) {
		if(executeAnySql) { // 执行了任何sql, 才有回滚的意义
			switch(remoteDatabaseOperateType) {
			case CREATE:
				execute(dropSql, RemoteDatabaseOperateType.DROP, false);
				break;
			case DROP:
				execute(createSql, RemoteDatabaseOperateType.CREATE, false);
				break;
			}
		}
	}
	
	public void executeCreate() {
		execute(createSql, RemoteDatabaseOperateType.CREATE, true);
	}
	
	private void executeDrop() {
		execute(dropSql, RemoteDatabaseOperateType.DROP, true);
		dropSql.clear();
	}

	@Override
	public void destroy() throws DestroyException {
		createSql.clear();
		if(destroy) {
			executeDrop();
		}
	}
}
