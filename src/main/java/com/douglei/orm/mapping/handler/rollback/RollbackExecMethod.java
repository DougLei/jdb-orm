package com.douglei.orm.mapping.handler.rollback;

/**
 * 回滚的执行方式
 * @author DougLei
 */
public enum RollbackExecMethod {
	EXEC_DDL_SQL, // 执行DDL SQL语句
	
	EXEC_ADD_MAPPING, // 执行添加映射
	EXEC_DELETE_MAPPING, // 执行删除映射
	
	EXEC_ADD_MAPPING_FEATURE, // 执行添加映射特性
	EXEC_DELETE_MAPPING_FEATURE, // 执行删除映射特性
	
	EXEC_CREATE_SERIALIZATION_FILE, // 创建序列化文件
	EXEC_DELETE_SERIALIZATION_FILE; // 删除序列化文件
}
