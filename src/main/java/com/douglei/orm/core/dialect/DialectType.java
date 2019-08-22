package com.douglei.orm.core.dialect;

import java.util.Arrays;

import com.douglei.orm.core.dialect.impl.mysql.MySqlDialect;
import com.douglei.orm.core.dialect.impl.oracle.OracleDialect;
import com.douglei.orm.core.dialect.impl.sqlserver.SqlServerDialect;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public enum DialectType {
	/**
	 * <b>主要是在指定配置属于哪个方言时使用</b>
	 */
	ALL,
	
	ORACLE(OracleDialect.class, new byte[] {11}),
	
	MYSQL(MySqlDialect.class, new byte[] {8}),
	
	SQLSERVER(SqlServerDialect.class, new byte[] {11});
	
	private Class<? extends Dialect> dialectClass;// 方言类
	private Dialect dialectInstance;// 方言实例
	private byte[] supportMajorVersions;// 支持的主版本, 版本号为主版本号
	
	private DialectType() {
	}
	private DialectType(Class<? extends Dialect> dialectClass, byte[] supportMajorVersions) {
		this.dialectClass = dialectClass;
		this.supportMajorVersions = supportMajorVersions;
	}

	/**
	 * 调用该方法, 传入的参数必须先调用.toUpperCase()方法
	 * @param dialect
	 * @return
	 */
	public static DialectType toValue(String dialect) {
		DialectType[] dts = DialectType.values();
		for (DialectType dt : dts) {
			if(dt.name().equals(dialect)) {
				return dt;
			}
		}
		return null;
	}
	
	/**
	 * 获取方言实例
	 * @return
	 */
	public Dialect getDialectInstance() {
		if(dialectInstance == null) {
			dialectInstance = (Dialect) ConstructorUtil.newInstance(dialectClass);
		}
		return dialectInstance;
	}
	
	public byte[] supportMajorVersions() {
		return supportMajorVersions;
	}
	
	@Override
	public String toString() {
		return "Database dialect=["+name()+"], 支持的主版本="+Arrays.toString(supportMajorVersions);
	}
	
	/**
	 * 支持的数据库
	 * @return
	 */
	public static String supportDatabase() {
		StringBuilder sb = new StringBuilder();
		DialectType[] dialectTypes = values_();
		for (DialectType dialectType : dialectTypes) {
			sb.append("\n");
			sb.append(dialectType.toString());
		}
		sb.append("\n");
		return sb.toString();
	}
	
	/**
	 * 返回不包括ALL的数组
	 * @return
	 */
	public static DialectType[] values_() {
		if(values_ == null) {
			DialectType[] origin = DialectType.values();
			values_ = new DialectType[origin.length - 1];
			
			byte i = 0;
			for (DialectType odt : origin) {
				if(odt != ALL) {
					values_[i++] = odt;
				}
			}
		}
		return values_;
	}
	private static DialectType[] values_;
}
