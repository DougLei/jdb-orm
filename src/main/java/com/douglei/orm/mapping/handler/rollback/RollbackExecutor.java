package com.douglei.orm.mapping.handler.rollback;

import java.sql.SQLException;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingProperty;
import com.douglei.orm.mapping.container.MappingContainer;
import com.douglei.orm.mapping.handler.object.DBConnection;

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
			case EXEC_ADD_MAPPING_PROPERTY:
				mappingContainer.addMappingProperty((MappingProperty)object);
				break;
			case EXEC_DELETE_MAPPING_PROPERTY:
				mappingContainer.deleteMappingProperty(object.toString());
				break;
			case EXEC_ADD_MAPPING:
				mappingContainer.addMapping((Mapping)object);
				break;
			case EXEC_DELETE_MAPPING:
				mappingContainer.deleteMapping(object.toString());
				break;
		}
	}
}
