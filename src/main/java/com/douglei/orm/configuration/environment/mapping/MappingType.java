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
	
	public static MappingType toValueByMappingConfigurationFileName(String mappingConfigurationFileName) {
		if(mappingConfigurationFileName.endsWith(TABLE.mappingFileSuffix)) {
			return TABLE;
		}else if(mappingConfigurationFileName.endsWith(SQL.mappingFileSuffix)) {
			return SQL;
		}
		throw new NullPointerException("传入的" + mappingConfigurationFileName + ", 没哟匹配到对应的MappingType");
	}
	
	/**
	 * 获取映射文件的后缀数组
	 * @return
	 */
	public static String[] getMappingFileSuffixArray() {
		if(mappingFileSuffixArray == null) {
			MappingType[] mts = MappingType.values();
			int length = mts.length;
			
			mappingFileSuffixArray = new String[length];
			for(int i=0;i<length;i++) {
				mappingFileSuffixArray[i] = mts[i].mappingFileSuffix;
			}
		}
		return mappingFileSuffixArray;
	}
	private static String[] mappingFileSuffixArray;
}
