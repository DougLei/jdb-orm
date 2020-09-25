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

	@Override
	public void create(ViewMetadata view) throws Exception {
		delete(view.getOldName()); // 不论怎样, 都先删除旧的
		validateNameExists(view); // 验证下新的名称是否可以使用
		
		connection.executeSql(view.getContent());
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, sqlStatementHandler.dropView(view.getName()), connection);
		
		serializationHandler.createFileDirectly(view, ViewMetadata.class);
	}

	@Override
	public void delete(String viewName) throws SQLException {
		ViewMetadata deleted = (ViewMetadata) serializationHandler.deleteFile(viewName, ViewMetadata.class);
		if(!connection.viewExists(viewName))
			return;
		
		String createContent; 
		if(deleted == null) {
			logger.info("不存在name为{}的视图序列化文件, 去数据库中查找视图内容", viewName);
			createContent = connection.queryViewContent(viewName);
		}else {
			createContent = deleted.getContent();
		}
		
		connection.executeSql(sqlStatementHandler.dropView(viewName));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, createContent, connection);
	}
}
