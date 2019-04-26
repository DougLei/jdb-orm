package com.douglei.database.dialect;

import java.util.HashMap;
import java.util.Map;

import com.douglei.database.dialect.impl.mssql.MSSqlDialect;
import com.douglei.database.dialect.impl.mysql.MySqlDialect;
import com.douglei.database.dialect.impl.oracle.OracleDialect;
import com.douglei.utils.StringUtil;

/**
 * dialect映射
 * @author DougLei
 */
public class DialectMapping {
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
		if(StringUtil.isEmpty(code)) {
			throw new NullPointerException("code的参数值不能为空");
		}
		
		Dialect dialect = DIALECT_MAP.get(code.trim().toUpperCase());
		if(dialect == null) {
			throw new NullPointerException("系统目前不支持["+code+"], 目前支持的code值包括: "+DIALECT_MAP.keySet());
		}
		return dialect;
	}
	
	/**
	 * 注册新的Dialect实例
	 * @param dialect
	 */
	public static void register(Dialect dialect) {
		if(dialect == null) {
			throw new NullPointerException("要注册的 "+Dialect.class+" 实例不能为空");
		}
		
		String code = dialect.getCode();
		if(DIALECT_MAP.containsKey(code)) {
			throw new RepeatDialectException("已经存在dialect-code值为["+code+"]的映射实例: " + DIALECT_MAP.get(code).getClass());
		}
		DIALECT_MAP.put(code, dialect);
	}
}
