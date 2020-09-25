package com.douglei.orm.core.mapping.rollback;

import java.sql.SQLException;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.container.MappingContainer;
import com.douglei.orm.core.mapping.serialization.SerializationHandler;
import com.douglei.orm.core.mapping.struct.DBConnection;
import com.douglei.orm.core.metadata.AbstractMetadata;

/**
 * 回滚的执行者
 * @author DougLei
 */
public class RollbackExecutor {
	private Object object; // 回滚时的执行对象
	private RollbackExecMethod method;
	
	private DBConnection connection;
	
	public RollbackExecutor(RollbackExecMethod method, Object object, DBConnection connection) {
		this.method = method;
		this.object = object;
		this.connection = connection;
	}
	
	/**
	 * 执行回滚
	 * @param mappingContainer
	 * @throws SQLException 
	 */
	public void executeRollback(MappingContainer mappingContainer) throws SQLException {
		switch(method) {
			case EXEC_DDL_SQL:
				connection.executeSql(object.toString());
				break;
			case EXEC_ADD_MAPPING:
				mappingContainer.addMapping((Mapping)object);
				break;
			case EXEC_DELETE_MAPPING:
				mappingContainer.deleteMapping(object.toString());
				break;
			case EXEC_CREATE_SERIALIZATION_FILE:
				SerializationHandler.getSingleton().createFileOnRollback((AbstractMetadata)object);
				break;
			case EXEC_DELETE_SERIALIZATION_FILE:
				SerializationHandler.getSingleton().deleteFileOnRollback(object.toString());
				break;
		}
	}
}
