package com.douglei.orm.core.mapping;

import java.io.File;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

/**
 * 表序列化处理器
 * @author DougLei
 */
class TableSerializationHandler {
	
	/**
	 * 获取对应的orm序列化文件全路径
	 * @param filename
	 * @return
	 */
	private String getOrmFilePath(String filename) {
		return TableSerializationFolderContainer.getFolder(EnvironmentContext.getEnvironmentProperty().getConfigurationId()) + filename;
	}
	
	/**
	 * 创建序列化文件
	 * @param table
	 */
	public void createFile(TableMetadata table) {
		String filePath = getOrmFilePath(table.getName());
		if(table.getName() == table.getOldName()) { // 没有改表名
			if(new File(filePath).exists()) { // 判断之前是否有同名文件存在
				TableMetadata oldTableMetadata = JdkSerializeProcessor.deserializeFromFile(TableMetadata.class, filePath);
				JdkSerializeProcessor.serialize2File(table, filePath);
				RollbackRecorder.record(RollbackExecMethod.EXEC_CREATE_SERIALIZATION_FILE, oldTableMetadata);
				return;
			}
		}else {
			deleteFile(table.getOldName());
		}
		JdkSerializeProcessor.serialize2File(table, filePath);
		RollbackRecorder.record(RollbackExecMethod.EXEC_DELETE_SERIALIZATION_FILE, table.getName());
	}

	/**
	 * 删除序列化文件
	 * @param tableName
	 */
	public void deleteFile(String tableName) {
		String filePath = getOrmFilePath(tableName);
		File file = new File(filePath);
		if(file.exists()) {
			TableMetadata oldTableMetadata = JdkSerializeProcessor.deserializeFromFile(TableMetadata.class, filePath);
			file.delete();
			RollbackRecorder.record(RollbackExecMethod.EXEC_CREATE_SERIALIZATION_FILE, oldTableMetadata);
		}
	}
	
	/**
	 * 反序列化获取TableMetadata对象
	 * @param tableName
	 * @return
	 */
	public TableMetadata deserialize(String tableName) {
		return JdkSerializeProcessor.deserializeFromFile(TableMetadata.class, getOrmFilePath(tableName));
	}
	
	// -------------------------------------------------------------------------------------------------------------------------
	/**
	 * 回滚时创建序列化文件
	 * @param table
	 */
	public void rollbackCreateFile(TableMetadata table) {
		JdkSerializeProcessor.serialize2File(table, getOrmFilePath(table.getName()));
	}
	
	/**
	 * 回滚时删除序列化文件
	 * @param tableName
	 */
	public void rollbackDeleteFile(String tableName) {
		File file = new File(getOrmFilePath(tableName));
		if(file.exists()) 
			file.delete();
	}
}
