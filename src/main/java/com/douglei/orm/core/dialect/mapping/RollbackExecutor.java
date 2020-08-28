package com.douglei.orm.core.dialect.mapping;

import java.sql.SQLException;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.core.metadata.table.TableMetadata;

/**
 * 回滚的执行者
 * @author DougLei
 */
class RollbackExecutor {
	private Object object; // 回滚时的执行对象
	private RollbackExecMethod method;

	public RollbackExecutor(RollbackExecMethod method, Object object) {
		this.method = method;
		this.object = object;
	}
	
	/**
	 * 执行回滚
	 * @param tableStructHandler
	 * @param mappingStore
	 * @throws SQLException 
	 */
	public void executeRollback(TableStructHandler tableStructHandler, MappingStore mappingStore) throws SQLException {
		switch(method) {
			case EXEC_DDL_SQL:
				tableStructHandler.executeSql(object.toString());
				break;
			case EXEC_ADD_MAPPING:
				mappingStore.addMapping((Mapping)object);
				break;
			case EXEC_DELETE_MAPPING:
				mappingStore.deleteMapping(object.toString());
				break;
			case EXEC_CREATE_SERIALIZATION_FILE:
				tableStructHandler.getTableserializationhandler().rollbackCreateFile((TableMetadata)object);
				break;
			case EXEC_DELETE_SERIALIZATION_FILE:
				tableStructHandler.getTableserializationhandler().rollbackDeleteFile(object.toString());
				break;
		}
	}
}
