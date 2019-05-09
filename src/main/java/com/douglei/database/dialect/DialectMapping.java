package com.douglei.database.dialect;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.impl.mysql.MySqlDialect;
import com.douglei.database.dialect.impl.oracle.OracleDialect;
import com.douglei.database.dialect.impl.sqlserver.SqlServerDialect;
import com.douglei.utils.StringUtil;



/**
 * dialect映射
 * @author DougLei
 */
public class DialectMapping {
	private static final Logger logger = LoggerFactory.getLogger(DialectMapping.class);
	
	private static final Map<String, Dialect> DIALECT_MAP = new HashMap<String, Dialect>(3);
	static {
		register(SqlServerDialect.singleInstance());
		register(MySqlDialect.singleInstance());
		register(OracleDialect.singleInstance());
	}
	
	/**
	 * 获取dialect实例
	 * @param databaseCode
	 * @return
	 */
	public static Dialect getDialect(String databaseCode) {
		logger.debug("获取databaseCode值为{} 的{}实例", databaseCode, Dialect.class);
		if(StringUtil.isEmpty(databaseCode)) {
			throw new NullPointerException("databaseCode的参数值不能为空");
		}
		
		Dialect dialect = DIALECT_MAP.get(databaseCode.toUpperCase());
		if(dialect == null) {
			throw new NullPointerException("系统目前不支持["+databaseCode+"], 目前支持的databaseCode值包括:"+DIALECT_MAP.keySet());
		}
		
		logger.debug("获取databaseCode值为{} 的{}实例", databaseCode, dialect.getClass());
		return dialect;
	}
	
	/**
	 * 根据jdbc的连接url获取对应的Dialect
	 * @param JDBCUrl
	 * @return
	 */
	public static Dialect getDialectByJDBCUrl(String JDBCUrl) {
		if(logger.isDebugEnabled()) {
			logger.debug("根据jdbc的连接url={}, 获取对应的Dialect", JDBCUrl);
		}
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
		return getDialect(JDBCUrl.substring(a, b-1));
	}
	
	public static void main(String[] args) {
		System.out.println(1/0);
	}
	
	/**
	 * 注册新的Dialect实例
	 * @param dialect
	 */
	private static void register(Dialect dialect) {
		String databaseCode = dialect.getDatabaseCode();
		DIALECT_MAP.put(databaseCode, dialect);
		if(logger.isDebugEnabled()) {
			logger.debug("注册databaseCode值为{} 的{}实例", databaseCode, dialect.getClass());
		}
	}
}
