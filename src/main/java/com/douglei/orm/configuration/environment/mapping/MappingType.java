package com.douglei.orm.configuration.environment.mapping;

/**
 * 映射的类型
 * @author DougLei
 */
public enum MappingType {
	TABLE(".tmp.xml"),
	SQL(".smp.xml"),
	PROC(".pmp.xml"),
	VIEW(".vmp.xml");
	
	private String fileSuffix;
	private MappingType(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	/**
	 * 根据文件获取对应的mapping类型
	 * @param file
	 * @return
	 */
	public static MappingType toValueByFile(String file) {
		if(file.endsWith(TABLE.fileSuffix))
			return TABLE;
		if(file.endsWith(SQL.fileSuffix))
			return SQL;
		if(file.endsWith(PROC.fileSuffix))
			return PROC;
		if(file.endsWith(VIEW.fileSuffix))
			return VIEW;
		throw new NullPointerException("传入的" + file + ", 没有匹配到对应的MappingType");
	}
	
	/**
	 * 获取映射文件的后缀数组
	 * @return
	 */
	public static String[] getFileSuffixes() {
		return new String[] {TABLE.fileSuffix, SQL.fileSuffix, PROC.fileSuffix, VIEW.fileSuffix};
	}
}
