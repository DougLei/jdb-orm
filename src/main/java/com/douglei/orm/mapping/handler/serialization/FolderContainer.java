package com.douglei.orm.mapping.handler.serialization;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.douglei.tools.utils.FileUtil;

/**
 * 
 * @author DougLei
 */
public class FolderContainer {
	private static final String VERSION = "v202009271"; // 序列化版本; 每次修改了映射相关的类的serialVersionUID值时, 都必须更新该值后, 再发布新版本; 版本值使用 "v+年月日+序列值" 来定义, 其中序列值每日从1开始, 同一日时递增
	private static final String FOLDER = System.getProperty("user.home") + File.separatorChar + ".orm" + File.separatorChar;
	private static final Map<String, String> FOLDER_MAP = new HashMap<String, String>(8); // orm序列化文件的根路径map, key是configuration id, value是对应的路径
	
	/**
	 * 创建文件夹
	 * @param configurationId
	 */
	public static synchronized void createFolder(String configurationId) {
		if(!FOLDER_MAP.containsKey(configurationId)) {
			String folderPath = FOLDER + configurationId + File.separatorChar + VERSION + File.separatorChar;
			FOLDER_MAP.put(configurationId, folderPath);
			
			File folder = new File(folderPath);
			if(!folder.exists()) 
				folder.mkdirs();
			
			File parentFolder = folder.getParentFile();
			String[] list = parentFolder.list();
			if(list != null && list.length > 1) {
				for (String f : list) {
					if(!f.equals(VERSION)) {
						FileUtil.delete(new File(parentFolder.getAbsolutePath() + File.separatorChar + f));
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
			FileUtil.delete(new File(folder).getParentFile());
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
