package com.douglei.orm.mapping.handler.object.view;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.handler.object.DBConnection;
import com.douglei.orm.mapping.handler.object.ObjectHandler;
import com.douglei.orm.mapping.handler.rollback.RollbackExecMethod;
import com.douglei.orm.mapping.handler.rollback.RollbackRecorder;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;

/**
 * 视图对象处理器
 * @author DougLei
 */
public class ViewObjectHandler extends ObjectHandler<ViewMetadata, String>{
	private static final Logger logger = LoggerFactory.getLogger(ViewObjectHandler.class);
	
	public ViewObjectHandler(DBConnection connection) {
		super(connection);
	}
	
	// 获取删除的sql语句
	protected String getDropSql(String name) {
		return sqlStatementHandler.dropView(name);
	}
	// 判断指定的name是否已经存在
	protected boolean nameExists(String name) throws SQLException {
		return connection.viewExists(name);
	}
	// 查询创建脚本
	protected String queryCreateScript(String name) throws SQLException {
		return connection.queryViewScript(name);
	}
	
	@Override
	public void create(ViewMetadata view, ViewMetadata exViewMetadata) throws Exception {
		if(nameExists(view.getOldName())) {
			switch(view.getCreateMode()) {
				case DROP_CREATE:
					delete_(false, view.getOldName(), exViewMetadata);
					break;
				default:
					return;
			}
		}
		validateNameExists(view); // 验证下新的名称是否可以使用
		
		connection.executeSql(view.getScript());
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, getDropSql(view.getName()), connection);
	}

	@Override
	public void delete(String viewName, ViewMetadata exViewMetadata) throws SQLException {
		delete_(true, viewName, exViewMetadata);
	}
	
	/**
	 * 
	 * @param isValidateNameExists 是否验证name是否存在 
	 * @param viewName
	 * @param exViewMetadata
	 * @throws SQLException
	 */
	private void delete_(boolean isValidateNameExists, String viewName, ViewMetadata exViewMetadata) throws SQLException {
		if(isValidateNameExists && !nameExists(viewName))
			return;
		
		String script; 
		if(exViewMetadata == null) {
			logger.debug("去数据库中查找名为[{}]的脚本", viewName);
			script = queryCreateScript(viewName);
		}else {
			script = exViewMetadata.getScript();
		}
		
		connection.executeSql(getDropSql(viewName));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, script, connection);
	}
}
