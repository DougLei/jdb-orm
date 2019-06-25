package com.douglei.orm.core.dialect.db.table.handler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.context.DBRunEnvironmentContext;

/**
 * 表序列化文件处理器
 * @author DougLei
 */
class TableSerializationFileHandler {
	
	// orm序列化文件的根路径map, key是configuration id, value是对应的路径
	private static final Map<String, String> ORM_SERIALIZATION_FILE_ROOT_PATH_MAP = new HashMap<String, String>(8);
	
	// 根据配置id, 获取对应的orm序列化文件的根路径
	private String getOrmSerializationFileRootPath() {
		String configurationId = DBRunEnvironmentContext.getConfigurationId();
		
		String ormSerializationFileRootPath = ORM_SERIALIZATION_FILE_ROOT_PATH_MAP.get(configurationId);
		if(ormSerializationFileRootPath == null) {
			ormSerializationFileRootPath = DBRunEnvironmentContext.getSerializationFileRootPath() + File.separator + configurationId + File.separator;
			File rootFile = new File(ormSerializationFileRootPath);
			if(!rootFile.exists()) {
				rootFile.mkdirs();
			}
			ORM_SERIALIZATION_FILE_ROOT_PATH_MAP.put(configurationId, ormSerializationFileRootPath);
		}
		return ormSerializationFileRootPath;
	}
	
}
