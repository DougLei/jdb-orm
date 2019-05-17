package com.douglei.configuration.environment.mapping;

/**
 * 映射的类型
 * @author DougLei
 */
public enum MappingType {
	TABLE(1, ".tmp.xml"),
	SQL(2, ".smp.xml");
	
	private int id;
	private String mappingFileSuffix;
	private MappingType(int id, String mappingFileSuffix) {
		this.id = id;
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
	
	/**
	 * 获取映射文件的后缀
	 * @return
	 */
	public static String[] getFinalMappingFileSuffix() {
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

	public int getId() {
		return id;
	}
}
