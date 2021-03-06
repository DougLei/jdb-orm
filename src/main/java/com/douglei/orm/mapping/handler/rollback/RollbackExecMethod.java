package com.douglei.orm.mapping.handler.rollback;

/**
 * 回滚的执行方式
 * @author DougLei
 */
public enum RollbackExecMethod {
	EXEC_DDL_SQL, // 执行DDL SQL语句

	EXEC_ADD_MAPPING_PROPERTY, // 执行添加映射属性
	EXEC_DELETE_MAPPING_PROPERTY, // 执行删除映射属性
	
	EXEC_ADD_MAPPING, // 执行添加映射
	EXEC_DELETE_MAPPING; // 执行删除映射
}
