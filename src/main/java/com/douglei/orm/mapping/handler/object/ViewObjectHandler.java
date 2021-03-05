package com.douglei.orm.mapping.handler.object;

import java.sql.SQLException;

import com.douglei.orm.mapping.handler.rollback.RollbackExecMethod;
import com.douglei.orm.mapping.handler.rollback.RollbackRecorder;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;

/**
 * 视图对象处理器
 * @author DougLei
 */
public class ViewObjectHandler extends DBObjectHandler {
	
	public ViewObjectHandler(DBConnection connection) {
		super(connection);
	}
	
	/**
	 * 是否存在指定name的视图/存储过程
	 * @param name
	 * @return
	 * @throws SQLException 
	 */
	protected boolean exists(String name) throws SQLException {
		return connection.viewExists(name);
	}
	
	/**
	 * 获取drop的sql语句
	 * @param name
	 * @return
	 */
	protected String getDropSqlStatement(String name) {
		return "drop view " + name;
	}
	
	/**
	 * 创建视图
	 * @param addViewMetadata 创建的视图元数据实例
	 * @param coveredViewMetadata 被覆盖的视图元数据实例, 如果是新添加的视图, 或框架启动时, 该参数为null
	 * @throws Exception
	 */
	public void create(ViewMetadata addViewMetadata, ViewMetadata coveredViewMetadata) throws Exception {
		if(coveredViewMetadata != null) 
			delete(coveredViewMetadata);
		else if(exists(addViewMetadata.getName()))
			delete(addViewMetadata);
		
		connection.executeSql(addViewMetadata.getScript());
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, getDropSqlStatement(addViewMetadata.getName()), connection);
	}

	/**
	 * 删除视图
	 * @param deletedViewMetadata 被删除的视图元数据实例
	 * @throws SQLException
	 */
	public void delete(ViewMetadata deletedViewMetadata) throws SQLException {
		connection.executeSql(getDropSqlStatement(deletedViewMetadata.getName()));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, deletedViewMetadata.getScript(), connection);
	}
}
