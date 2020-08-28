package com.douglei.orm.configuration.environment.mapping;

/**
 * 映射的类型
 * @author DougLei
 */
public enum MappingType {
	TABLE(".tmp.xml"),
	SQL(".smp.xml");
	
	private String mappingFileSuffix;
	private MappingType(String mappingFileSuffix) {
		this.mappingFileSuffix = mappingFileSuffix;
	}

	/**
	 * 根据type获取对应的枚举值
	 * @param type
	 * @return
	 */
	public static MappingType toValue(String type) {
		type = type.toUpperCase();
		MappingType[] mts = MappingType.values();
		for (MappingType mappingType : mts) {
			if(mappingType.name().equals(type)) {
				return mappingType;
			}
		}
		return null;
	}
	
	/**
	 * 根据文件获取对应的枚举值
	 * @param file
	 * @return
	 */
	public static MappingType toValueByFile(String file) {
		if(file.endsWith(TABLE.mappingFileSuffix)) {
			return TABLE;
		}else if(file.endsWith(SQL.mappingFileSuffix)) {
			return SQL;
		}
		throw new NullPointerException("传入的" + file + ", 没有匹配到对应的MappingType");
	}
	
	/**
	 * 获取映射文件的后缀数组
	 * @return
	 */
	public static String[] getMappingFileSuffixs() {
		return new String[] {TABLE.mappingFileSuffix, SQL.mappingFileSuffix};
	}
}
