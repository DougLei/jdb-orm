package com.douglei.configuration.environment.mapping;

import java.util.Arrays;

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

	
	public static MappingType toValue(String value) {
		value = value.trim().toUpperCase();
		
		MappingType[] mts = MappingType.values();
		for (MappingType mappingType : mts) {
			if(mappingType.name().equals(value)) {
				return mappingType;
			}
		}
		throw new IllegalArgumentException("配置的值[\""+value+"\"]错误, 目前支持的值包括：["+Arrays.toString(mts)+"]");
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
