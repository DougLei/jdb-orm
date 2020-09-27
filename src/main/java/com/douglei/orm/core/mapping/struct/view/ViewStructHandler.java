package com.douglei.orm.core.mapping.struct.view;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.mapping.rollback.RollbackExecMethod;
import com.douglei.orm.core.mapping.rollback.RollbackRecorder;
import com.douglei.orm.core.mapping.struct.DBConnection;
import com.douglei.orm.core.mapping.struct.StructHandler;
import com.douglei.orm.core.metadata.view.ViewMetadata;

/**
 * 视图结构处理器
 * @author DougLei
 */
public class ViewStructHandler extends StructHandler<ViewMetadata, String>{
	private static final Logger logger = LoggerFactory.getLogger(ViewStructHandler.class);
	
	public ViewStructHandler(DBConnection connection) {
		super(connection);
	}
	
	// 获取删除的sql语句
	protected String getDropSql(String name) {
		return sqlStatementHandler.dropView(name);
	}
	// 获取序列化的class
	protected Class<? extends ViewMetadata> getSerialClass(){
		return ViewMetadata.class;
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
	public void create(ViewMetadata view) throws Exception {
		delete(view.getOldName()); // 不论怎样, 都先删除旧的
		validateNameExists(view); // 验证下新的名称是否可以使用
		
		connection.executeSql(view.getScript());
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, getDropSql(view.getName()), connection);
		
		serializationHandler.createFileDirectly(view, getSerialClass());
	}

	/**
	 * 
	 * @param viewName 传入的viewName必须为大写
	 */
	@Override
	public void delete(String viewName) throws SQLException {
		ViewMetadata deleted = (ViewMetadata) serializationHandler.deleteFile(viewName, getSerialClass());
		if(!nameExists(viewName))
			return;
		
		String script; 
		if(deleted == null) {
			logger.debug("不存在name为{}的 [{}] 序列化文件, 去数据库中查找对应的脚本", viewName, getSerialClass());
			script = queryCreateScript(viewName);
		}else {
			script = deleted.getScript();
		}
		
		connection.executeSql(getDropSql(viewName));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, script, connection);
	}
}
