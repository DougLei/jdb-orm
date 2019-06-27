package com.douglei.orm.core.dialect.db.table.handler;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.dialect.db.table.handler.serializationobject.SerializeObjectHolder;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

/**
 * 表序列化文件处理器
 * @author DougLei
 */
class TableSerializationFileHandler {
	private static final Logger logger = LoggerFactory.getLogger(TableSerializationFileHandler.class);
	
	// 根文件夹名称
	private static final String ROOT_FOLDER_NAME = "orm-serialization-files";
	
	// 序列化文件的后缀
	private static final String SERIALIZATION_FILE_SUFFIX = ".orm";
	
	// orm序列化文件的根路径map, key是configuration id, value是对应的路径
	private static final Map<String, String> ORM_SERIALIZATION_FILE_ROOT_PATH_MAP = new HashMap<String, String>(8);
	
	// 获取对应的orm序列化文件路径, 包括文件名
	private String getOrmSerializationFilePath(String serializationFileName) {
		
		String configurationId = DBRunEnvironmentContext.getEnvironmentProperty().getId();
		String ormSerializationFileRootPath = ORM_SERIALIZATION_FILE_ROOT_PATH_MAP.get(configurationId);
		if(ormSerializationFileRootPath == null) {
			ormSerializationFileRootPath = DBRunEnvironmentContext.getEnvironmentProperty().getSerializationFileRootPath() + File.separator + ROOT_FOLDER_NAME + File.separator + configurationId + File.separator;
			File rootFile = new File(ormSerializationFileRootPath);
			if(!rootFile.exists()) {
				rootFile.mkdirs();
			}
			ORM_SERIALIZATION_FILE_ROOT_PATH_MAP.put(configurationId, ormSerializationFileRootPath);
		}
		return ormSerializationFileRootPath + serializationFileName + SERIALIZATION_FILE_SUFFIX;
	}

	// 是否启用表的动态更新
	private boolean getEnableTableDynamicUpdate() {
		return DBRunEnvironmentContext.getEnvironmentProperty().getEnableTableDynamicUpdate();
	}
	
	/**
	 * 创建序列化文件
	 * @param table
	 * @param serializeObjectHolders
	 */
	public void createSerializationFile(TableMetadata table, List<SerializeObjectHolder> serializeObjectHolders) {
		if(getEnableTableDynamicUpdate()) {
			JdkSerializeProcessor.serialize2File(table, getOrmSerializationFilePath(table.getName()));
			if(serializeObjectHolders != null) {
				serializeObjectHolders.add(new SerializeObjectHolder(table, null));
			}
		}
	}

	/**
	 * 更新序列化文件
	 * @param table
	 * @param oldTable
	 * @param serializeObjectHolders
	 */
	public void updateSerializationFile(TableMetadata table, TableMetadata oldTable, List<SerializeObjectHolder> serializeObjectHolders) {
		if(!table.getName().equals(oldTable.getName())) {// 新旧表名不一样, 序列化文件名才不一样, 才需要删除旧的序列化文件, 如果表名一样, 则直接覆盖
			dropSerializationFile(oldTable, null);
		}
		createSerializationFile(table, null);
		if(serializeObjectHolders != null) {
			serializeObjectHolders.add(new SerializeObjectHolder(table, oldTable));
		}
	}
	
	/**
	 * 删除序列化文件
	 * @param table
	 * @param serializeObjectHolders
	 */
	public void dropSerializationFile(TableMetadata table, List<SerializeObjectHolder> serializeObjectHolders) {
		if(getEnableTableDynamicUpdate()) {
			File file = new File(getOrmSerializationFilePath(table.getName()));
			if(file.exists()) {
				file.delete();
				if(serializeObjectHolders != null) {
					serializeObjectHolders.add(new SerializeObjectHolder(null, table));
				}
			}
		}
	}
	
	/**
	 * 反序列化文件, 获取TableMetadata对象
	 * @param table
	 * @return
	 */
	public TableMetadata deserializeFromFile(TableMetadata table) {
		return JdkSerializeProcessor.deserializeFromFile(TableMetadata.class, getOrmSerializationFilePath(table.getOldName()));
	}
	
	/**
	 * 回滚序列化文件操作
	 * @param serializeObjectHolders
	 */
	public void rollbackSerializationFile(List<SerializeObjectHolder> serializeObjectHolders) {
		if(getEnableTableDynamicUpdate() && serializeObjectHolders.size() > 0) {
			logger.debug("开始回滚 序列化文件操作");
			for (SerializeObjectHolder holder : serializeObjectHolders) {
				switch(holder.getSerializeOPType()) {
					case CREATE:
						logger.debug("逆向: SerializeOPType=create, 删除序列化文件");
						dropSerializationFile(holder.getTable(), null);
						break;
					case UPDATE:
						logger.debug("逆向: SerializeOPType=update, 删除新的序列化文件, 恢复之前被覆盖的序列化文件");
						if(!holder.getTable().getName().equals(holder.getOldTable().getName())) {// 新旧表名不一样, 序列化文件名才不一样, 回滚时才需要删除刚刚创建的序列化文件, 如果表名一样, 则直接覆盖
							dropSerializationFile(holder.getTable(), null);
						}
						createSerializationFile(holder.getOldTable(), null);
						break;
					case DROP:
						logger.debug("逆向: SerializeOPType=drop, 恢复之前被删除的序列化文件");
						createSerializationFile(holder.getOldTable(), null);
						break;
				}
			}
		}
	}
}
