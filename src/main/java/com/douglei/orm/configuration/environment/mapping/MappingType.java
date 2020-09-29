package com.douglei.orm.configuration.environment.mapping;

/**
 * 映射的类型
 * @author DougLei
 */
public enum MappingType {
	TABLE(".tmp.xml", 10, true),
	VIEW(".vmp.xml", 20, false),
	PROCEDURE(".pmp.xml", 30, false),
	SQL(".smp.xml", 40, true);
	
	private int priority; // 优先级, 越低越优先
	private String fileSuffix;
	private String name; 
	private boolean opMappingContainer; // 能否操作映射容器
	private MappingType(String fileSuffix, int priority, boolean opMappingContainer) {
		this.fileSuffix = fileSuffix;
		this.priority = priority;
		this.opMappingContainer = opMappingContainer;
		this.name = name().toLowerCase();
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
		if(file.endsWith(PROCEDURE.fileSuffix))
			return PROCEDURE;
		if(file.endsWith(VIEW.fileSuffix))
			return VIEW;
		throw new NullPointerException("传入的" + file + ", 没有匹配到对应的MappingType");
	}
	
	/**
	 * 获取映射文件的后缀数组
	 * @return
	 */
	public static String[] getFileSuffixes() {
		return new String[] {TABLE.fileSuffix, SQL.fileSuffix, PROCEDURE.fileSuffix, VIEW.fileSuffix};
	}

	/**
	 * 获取优先级
	 * @return
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * 获取映射文件后缀
	 * @return
	 */
	public String getFileSuffix() {
		return fileSuffix;
	}

	/**
	 * 能否操作映射容器
	 * @return
	 */
	public boolean opMappingContainer() {
		return opMappingContainer;
	}
	
	/**
	 * 获取name
	 * @return
	 */
	public String getName() {
		return name;
	}
}
