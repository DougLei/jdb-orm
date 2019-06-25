package com.douglei.orm.core.dialect;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.DatabaseMetadata;
import com.douglei.orm.core.dialect.impl.mysql.MySqlDialect;
import com.douglei.orm.core.dialect.impl.oracle.OracleDialect;
import com.douglei.orm.core.dialect.impl.sqlserver.SqlServerDialect;
import com.douglei.tools.utils.reflect.ConstructorUtil;



/**
 * dialect映射
 * @author DougLei
 */
public class DialectMapping {
	private static final Logger logger = LoggerFactory.getLogger(DialectMapping.class);
	private static final int count = DialectType.values().length;
	
	private static final Map<String, Class<? extends Dialect>> DIALECT_CLASS_MAP = new HashMap<String, Class<? extends Dialect>>(count);// Dialect类映射
	private static final Map<String, Dialect> DIALECT_MAP = new HashMap<String, Dialect>(count);// Dialect实例映射
	
	static {
		registerDialectClass(SqlServerDialect.DIALECT_TYPE.name(), SqlServerDialect.class);
		registerDialectClass(MySqlDialect.DIALECT_TYPE.name(), MySqlDialect.class);
		registerDialectClass(OracleDialect.DIALECT_TYPE.name(), OracleDialect.class);
	}
	// 注册新的Dialect类
	private static void registerDialectClass(String databaseCode, Class<? extends Dialect> dialectClass) {
		if(logger.isDebugEnabled()) {
			logger.debug("注册databaseCode值为{} 的class={}", databaseCode, dialectClass.getName());
		}
		DIALECT_CLASS_MAP.put(databaseCode, dialectClass);
	}
	
	// 内置的获取dialect实例, 不对参数进行大写转换
	private static Dialect getDialect_(String databaseCode) {
		Dialect dialect = DIALECT_MAP.get(databaseCode);
		if(dialect == null) {
			Class<? extends Dialect> dialectClass = DIALECT_CLASS_MAP.get(databaseCode);
			if(dialectClass == null) {
				throw new UnsupportDialectException("系统目前不支持["+databaseCode+"], 目前支持的数据库dialect包括:"+DialectType.supportDatabase());
			}
			dialect = (Dialect) ConstructorUtil.newInstance(dialectClass);
			DIALECT_MAP.put(databaseCode, dialect);
		}
		
		logger.debug("获取databaseCode值为{} 的{}实例", databaseCode, dialect.getClass());
		return dialect;
	}
	
	/**
	 * 获取dialect实例
	 * @param databaseCode
	 * @return
	 */
	public static Dialect getDialect(String databaseCode) {
		return getDialect_(databaseCode.toUpperCase());
	}
	
	/**
	 * 根据数据库元数据, 获取对应的Dialect
	 * @param databaseMetadata
	 * @return
	 */
	public static Dialect getDialectByDatabaseMetadata(DatabaseMetadata databaseMetadata) {
		if(logger.isDebugEnabled()) {
			logger.debug("根据数据库元数据, 获取对应的Dialect", databaseMetadata);
		}
		DialectType dialectType = getDialectTypeByDatabaseMetadata(databaseMetadata);
		return getDialect_(dialectType.name());
	}
	
	/**
	 * 根据数据库元数据, 获取对应的DialectType
	 * @param databaseMetadata
	 * @return
	 */
	private static DialectType getDialectTypeByDatabaseMetadata(DatabaseMetadata databaseMetadata) {
		String databaseProductName = extractDatabaseProductName(databaseMetadata.getJDBCUrl());
		DialectType dt = DialectType.toValue(databaseProductName);
		if(dt == null || dt.supportMajorVersions() == null) {
			throw new UnsupportDialectException("系统目前不支持["+databaseProductName+"], 目前支持的数据库dialect包括:"+DialectType.supportDatabase());
		}
		
		for(int supportMajorVersion : dt.supportMajorVersions()) {
			if(supportMajorVersion == databaseMetadata.getDatabaseMajorVersion()) {
				return dt;
			}
		}
		throw new UnsupportDialectException("系统目前不支持["+databaseProductName+"], 主版本为["+databaseMetadata.getDatabaseMajorVersion()+"], 目前支持的数据库dialect包括:"+DialectType.supportDatabase());
	}
	
	// 从jdbc url中提取出数据库产品名
	private static String extractDatabaseProductName(String JDBCUrl) {
		int i=0, a=0, b=0;
		while(a==0) {
			if(JDBCUrl.charAt(i++) == ':') {
				a = i;
			}
		}
		while(b==0) {
			if(JDBCUrl.charAt(i++) == ':') {
				b = i;
			}
		}
		if(b-1 <= a) {
			throw new ArithmeticException("JDBCUrl=" + JDBCUrl + ", 无法从中截取到对应的数据库类型信息, 第一个冒号的下标="+a+", 第二个冒号的下标="+b);
		}
		if(logger.isDebugEnabled()) {
			logger.debug("从 JDBCUrl= {}中, 提取的数据库产品名称为 {}", JDBCUrl, JDBCUrl.substring(a, b-1));
		}
		return JDBCUrl.substring(a, b-1);
	}
}
