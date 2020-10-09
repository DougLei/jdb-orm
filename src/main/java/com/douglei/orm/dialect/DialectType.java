package com.douglei.orm.dialect;

import java.util.Arrays;

import com.douglei.orm.dialect.impl.mysql.MySqlDialect;
import com.douglei.orm.dialect.impl.oracle.OracleDialect;
import com.douglei.orm.dialect.impl.sqlserver.SqlServerDialect;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public enum DialectType {
	MYSQL(MySqlDialect.class, 8),
	ORACLE(OracleDialect.class, 11),
	SQLSERVER(SqlServerDialect.class, 11);
	
	private Class<? extends Dialect> targetClass;
	private String name;
	private int[] supportDatabaseMajorVersions;
	
	private DialectType(Class<? extends Dialect> targetClass, int... supportDatabaseMajorVersions) {
		this.targetClass = targetClass;
		this.name = name();
		this.supportDatabaseMajorVersions = supportDatabaseMajorVersions;
	}

	/**
	 * 创建方言实例
	 * @return
	 */
	public Dialect newDialectInstance(){
		return (Dialect) ConstructorUtil.newInstance(targetClass);
	}
	
	/**
	 * 获取方言名
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 支持的数据库主版本
	 * @return
	 */
	public int[] supportDatabaseMajorVersions() {
		return supportDatabaseMajorVersions;
	}
	
	/**
	 * 当前方言是否支持参数中的数据库
	 * @param key
	 * @return
	 */
	public boolean support(DialectKey key) {
		if(!name.equalsIgnoreCase(key.getName())) 
			return false;
		
		for(int version : supportDatabaseMajorVersions) {
			if(version == key.getDatabaseMajorVersion())
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return " [name=" + name + ", supportDatabaseMajorVersions=" + Arrays.toString(supportDatabaseMajorVersions) + "] ";
	}
}
