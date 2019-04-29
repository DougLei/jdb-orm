package com.douglei.database.dialect;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import com.douglei.database.dialect.impl.mssql.MSSqlDialect;
import com.douglei.database.dialect.impl.mysql.MySqlDialect;
import com.douglei.database.dialect.impl.oracle.OracleDialect;
import com.douglei.utils.StringUtil;

/**
 * dialect映射
 * @author DougLei
 */
public class DialectMapping {
	private static final Logger logger = LoggerFactory.getLogger(DialectMapping.class);
	
	private static final Map<String, Dialect> DIALECT_MAP = new HashMap<String, Dialect>(16);
	static {
		register(new MSSqlDialect());
		register(new MySqlDialect());
		register(new OracleDialect());
	}
	
	/**
	 * 获取dialect实例
	 * @param code
	 * @return
	 */
	public static Dialect getDialect(String code) {
		logger.debug("获取code值为{} 的{}实例", code, Dialect.class);
		if(StringUtil.isEmpty(code)) {
			logger.error("code的参数值不能为空");
			throw new NullPointerException("code的参数值不能为空");
		}
		
		Dialect dialect = DIALECT_MAP.get(code.trim().toUpperCase());
		if(dialect == null) {
			logger.error("系统目前不支持[{}], 目前支持的code值包括:{}", code, DIALECT_MAP.keySet());
			throw new NullPointerException("系统目前不支持["+code+"], 目前支持的code值包括:"+DIALECT_MAP.keySet());
		}
		
		logger.debug("获取code值为{} 的{}实例", code, dialect.getClass());
		return dialect;
	}
	
	/**
	 * 注册新的Dialect实例
	 * @param dialect
	 */
	public static void register(Dialect dialect) {
		if(dialect == null) {
			logger.error("要注册的 {} 实例不能为空", Dialect.class);
			throw new NullPointerException("要注册的 "+Dialect.class+" 实例不能为空");
		}
		
		String code = dialect.getCode();
		if(DIALECT_MAP.containsKey(code)) {
			logger.error("已经存在dialect-code值为[{}]的映射实例:{}", code, DIALECT_MAP.get(code).getClass());
			throw new RepeatDialectException("已经存在dialect-code值为["+code+"]的映射实例:" + DIALECT_MAP.get(code).getClass());
		}
		DIALECT_MAP.put(code, dialect);
		if(logger.isDebugEnabled()) {
			logger.debug("注册code值为{} 的{}实例", code, dialect.getClass());
		}
	}
}
