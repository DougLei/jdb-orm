package com.douglei.orm.core.dialect.mapping;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

/**
 * 表序列化处理器
 * @author DougLei
 */
class TableSerializationHandler {
	private static final String VERSION = "v202009021"; // 序列化版本; 每次修改了映射相关的类的serialVersionUID值时, 都必须更新该值后, 再发布新版本; 版本值使用 "v+年月日+序列值" 来定义, 其中序列值每日从1开始, 同一日时递增
	private static final String FOLDER_NAME = ".orm"; // 文件夹名称
	private static final String SERIALIZATION_FILE_SUFFIX = FOLDER_NAME; // 序列化文件的后缀, 和文件夹名称一致
	private static final Map<String, String> ORM_SERIALIZATION_FILE_FOLDER_PATH_MAP = new HashMap<String, String>(8); // orm序列化文件的根路径map, key是configuration id, value是对应的路径
	
	// 获取对应的orm序列化文件夹路径, 包括文件名
	private String getOrmSerializationFilePath(String serializationFileName) {
		String configurationId = EnvironmentContext.getEnvironmentProperty().getId();
		String jdbOrmSerializationFileFolderPath = ORM_SERIALIZATION_FILE_FOLDER_PATH_MAP.get(configurationId);
		if(jdbOrmSerializationFileFolderPath == null) {
			jdbOrmSerializationFileFolderPath = EnvironmentContext.getEnvironmentProperty().getSerializationFileRootPath() + File.separatorChar + FOLDER_NAME + File.separatorChar + configurationId + File.separatorChar + VERSION + File.pathSeparatorChar;
			File folder = new File(jdbOrmSerializationFileFolderPath);
			if(!folder.exists()) 
				folder.mkdirs();
			ORM_SERIALIZATION_FILE_FOLDER_PATH_MAP.put(configurationId, jdbOrmSerializationFileFolderPath);
		}
		return jdbOrmSerializationFileFolderPath + serializationFileName + SERIALIZATION_FILE_SUFFIX;
	}
	
	/**
	 * 创建序列化文件
	 * @param table
	 */
	public void createFile(TableMetadata table) {
		String filePath = getOrmSerializationFilePath(table.getName());
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
		String filePath = getOrmSerializationFilePath(tableName);
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
		return JdkSerializeProcessor.deserializeFromFile(TableMetadata.class, getOrmSerializationFilePath(tableName));
	}
	
	// -------------------------------------------------------------------------------------------------------------------------
	/**
	 * 回滚时创建序列化文件
	 * @param table
	 */
	public void rollbackCreateFile(TableMetadata table) {
		JdkSerializeProcessor.serialize2File(table, getOrmSerializationFilePath(table.getName()));
	}
	
	/**
	 * 回滚时删除序列化文件
	 * @param tableName
	 */
	public void rollbackDeleteFile(String tableName) {
		File file = new File(getOrmSerializationFilePath(tableName));
		if(file.exists()) 
			file.delete();
	}
}
