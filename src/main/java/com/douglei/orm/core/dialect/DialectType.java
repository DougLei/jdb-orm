package com.douglei.orm.core.dialect;

import java.util.Arrays;

/**
 * 
 * @author DougLei
 */
public enum DialectType {
	/**
	 * <b>主要是给sql映射用的, table映射禁用</b>
	 */
	ALL,
	
	ORACLE(new int[] {11}),
	
	MYSQL(new int[] {8}),
	
	SQLSERVER(new int[] {11});
	
	private int[] supportMajorVersions;// 支持的主版本, 版本号为主版本号
	
	
	private DialectType() {
	}
	private DialectType(int[] supportMajorVersions) {
		this.supportMajorVersions = supportMajorVersions;
	}

	public static DialectType toValue(String dialect) {
		dialect = dialect.toUpperCase();
		DialectType[] dts = DialectType.values();
		for (DialectType dt : dts) {
			if(dt.name().equals(dialect)) {
				return dt;
			}
		}
		return null;
	}
	
	public int[] supportMajorVersions() {
		return supportMajorVersions;
	}
	
	@Override
	public String toString() {
		return "Database dialect=["+name()+"], 支持的主版本包括="+Arrays.toString(supportMajorVersions);
	}
	
	public static String supportDatabase() {
		StringBuilder sb = new StringBuilder();
		DialectType[] dialectTypes = DialectType.values();
		for (DialectType dialectType : dialectTypes) {
			if(dialectType == ALL) {
				continue;
			}
			sb.append(dialectType.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}
