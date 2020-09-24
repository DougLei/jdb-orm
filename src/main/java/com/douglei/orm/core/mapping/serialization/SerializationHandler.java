package com.douglei.orm.core.mapping.serialization;

import java.io.File;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.core.mapping.rollback.RollbackExecMethod;
import com.douglei.orm.core.mapping.rollback.RollbackRecorder;
import com.douglei.orm.core.metadata.AbstractMetadata;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

/**
 * 表序列化处理器
 * @author DougLei
 */
public class SerializationHandler {
	private SerializationHandler() {}
	private static final SerializationHandler singleton = new SerializationHandler();
	public static SerializationHandler getSingleton() {
		return singleton;
	}
	
	
	// 获取对应的orm序列化文件全路径
	private String getOrmFilePath(String filename) {
		return FolderContainer.getFolder(EnvironmentContext.getProperty().getConfigurationId()) + filename;
	}
	
	/**
	 * 创建序列化文件
	 * @param metadata
	 * @param clazz
	 */
	public void createFile(AbstractMetadata metadata, Class<? extends AbstractMetadata> clazz) {
		String filePath = getOrmFilePath(metadata.getName());
		if(metadata.getName() == metadata.getOldName()) { // 没有改表名
			if(new File(filePath).exists()) { // 判断之前是否有同名文件存在
				AbstractMetadata oldMetadata = JdkSerializeProcessor.deserializeFromFile(clazz, filePath);
				JdkSerializeProcessor.serialize2File(metadata, filePath);
				RollbackRecorder.record(RollbackExecMethod.EXEC_CREATE_SERIALIZATION_FILE, oldMetadata, null);
				return;
			}
		}else {
			deleteFile(metadata.getOldName(), clazz);
		}
		JdkSerializeProcessor.serialize2File(metadata, filePath);
		RollbackRecorder.record(RollbackExecMethod.EXEC_DELETE_SERIALIZATION_FILE, metadata.getName(), null);
	}

	/**
	 * 删除序列化文件
	 * @param name
	 * @param clazz
	 */
	public void deleteFile(String name, Class<? extends AbstractMetadata> clazz) {
		String filePath = getOrmFilePath(name);
		File file = new File(filePath);
		if(file.exists()) {
			AbstractMetadata oldMetadata = JdkSerializeProcessor.deserializeFromFile(clazz, filePath);
			file.delete();
			RollbackRecorder.record(RollbackExecMethod.EXEC_CREATE_SERIALIZATION_FILE, oldMetadata, null);
		}
	}
	
	/**
	 * 反序列化获取AbstractMetadata对象
	 * @param name
	 * @param clazz
	 * @return
	 */
	public AbstractMetadata deserialize(String name, Class<? extends AbstractMetadata> clazz) {
		return JdkSerializeProcessor.deserializeFromFile(clazz, getOrmFilePath(name));
	}
	
	
	// -------------------------------------------------------------------------------------------------------------------------
	/**
	 * 回滚时创建序列化文件
	 * @param metadata
	 */
	public void rollbackCreateFile(AbstractMetadata metadata) {
		JdkSerializeProcessor.serialize2File(metadata, getOrmFilePath(metadata.getName()));
	}
	
	/**
	 * 回滚时删除序列化文件
	 * @param name
	 */
	public void rollbackDeleteFile(String name) {
		File file = new File(getOrmFilePath(name));
		if(file.exists()) 
			file.delete();
	}
}
