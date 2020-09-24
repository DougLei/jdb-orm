package com.douglei.orm.core.mapping.struct.view;

import java.sql.SQLException;

import com.douglei.orm.core.dialect.db.object.DBObjectHandler;
import com.douglei.orm.core.dialect.db.sql.SqlStatementHandler;
import com.douglei.orm.core.mapping.rollback.RollbackExecMethod;
import com.douglei.orm.core.mapping.rollback.RollbackRecorder;
import com.douglei.orm.core.mapping.struct.DBConnection;
import com.douglei.orm.core.mapping.struct.StructHandler;
import com.douglei.orm.core.metadata.CreateMode;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.view.ViewMetadata;

/**
 * 视图结构处理器
 * @author DougLei
 */
public class ViewStructHandler extends StructHandler<ViewMetadata, String>{
	
	public ViewStructHandler(DBConnection connection) {
		super(connection);
	}

	// 创建视图
	private void createView(ViewMetadata view) throws SQLException {
		connection.executeSql(view.getContent());
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, dbObjectHandler.viewDropSqlStatement(view.getName()), connection);
	}
	
	// 删除视图
	private void dropView(String name) throws SQLException {
		connection.executeSql(dbObjectHandler.viewDropSqlStatement(name));
		
	}
	
	
	@Override
	public void create(ViewMetadata view) throws Exception {
		if(view.getCreateMode() == CreateMode.NONE)
			return;
		
		if(connection.tableExists(table.getOldName())) {
			switch(table.getCreateMode()) {
				case DROP_CREATE:
					dropPrimaryKeySequence(table.getPrimaryKeySequence());
					dropIndexes(table.getIndexes());
					dropConstraints(table.getConstraints());
					dropTable(table);
					break;
				case DYNAMIC_UPDATE:
					if(updateTable(table))
						serializationHandler.createFile(table, TableMetadata.class);
					return;
				default:
					return;
			}
		}
		createView(view);
		
		if(view.getCreateMode() == CreateMode.DYNAMIC_UPDATE)
			serializationHandler.createFile(view, ViewMetadata.class);
	}

	@Override
	public void delete(String name) throws SQLException {
		// TODO Auto-generated method stubz 
		
	}
}
