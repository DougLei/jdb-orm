package com.douglei.orm.core.mapping;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.douglei.tools.utils.IOUtil;

/**
 * 表序列化的文件夹容器, 使用Map实现, key为Configuration的id, value为对应的文件夹路径
 * @author DougLei
 */
public class TableSerializationFolderContainer {
	private static final String VERSION = "v202009151"; // 序列化版本; 每次修改了映射相关的类的serialVersionUID值时, 都必须更新该值后, 再发布新版本; 版本值使用 "v+年月日+序列值" 来定义, 其中序列值每日从1开始, 同一日时递增
	private static final String FOLDER_NAME = ".orm"; // 文件夹名称
	private static final Map<String, String> FOLDER_MAP = new HashMap<String, String>(8); // orm序列化文件的根路径map, key是configuration id, value是对应的路径
	
	// 获取对应的orm序列化文件夹路径, 包括文件名
//		private String getOrmFilePath(String filename) {
//			String configurationId = EnvironmentContext.getEnvironmentProperty().getConfigurationId();
//			String folderPath = FOLDER_PATH_MAP.get(configurationId);
//			if(folderPath == null) {
//				folderPath = EnvironmentContext.getEnvironmentProperty().getSerializationFileRootPath() + File.separatorChar + FOLDER_NAME + File.separatorChar + configurationId + File.separatorChar + VERSION + File.separatorChar;
//				File folder = new File(folderPath);
//				if(!folder.exists()) 
//					folder.mkdirs();
//				deletePreviousVersionOfFolder(folder.getParentFile());
//				FOLDER_PATH_MAP.put(configurationId, folderPath);
//			}
//			return folderPath + filename + FILE_SUFFIX;
//		}
	
	/**
	 * 创建文件夹
	 * @param serializationFileRootPath
	 * @param configurationId
	 */
	public static synchronized void createFolder(String serializationFileRootPath, String configurationId) {
		if(!FOLDER_MAP.containsKey(configurationId)) {
			String folderPath = serializationFileRootPath + File.separatorChar + FOLDER_NAME + File.separatorChar + configurationId + File.separatorChar + VERSION + File.separatorChar;
			FOLDER_MAP.put(configurationId, folderPath);
			
			File folder = new File(folderPath);
			if(!folder.exists()) 
				folder.mkdirs();
			
			File parentFolder = folder.getParentFile();
			String[] list = parentFolder.list();
			if(list != null && list.length > 1) {
				for (String f : list) {
					if(!f.equals(VERSION)) {
						IOUtil.delete(new File(parentFolder.getAbsolutePath() + File.separatorChar + f));
					}
				}
			}
		}
	}

	/**
	 * 删除文件夹
	 * @param configurationId
	 */
	public static synchronized void deleteFolder(String configurationId) {
		String folder = FOLDER_MAP.remove(configurationId);
		if(folder != null)
			IOUtil.delete(new File(folder).getParentFile());
	}
	
	/**
	 * 获取文件夹
	 * @param configurationId
	 * @return
	 */
	static String getFolder(String configurationId) {
		return FOLDER_MAP.get(configurationId);
	}
}
