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
	MYSQL("MYSQL", MySqlDialect.class, 8),
	ORACLE("ORACLE", OracleDialect.class, 11),
	SQLSERVER("SQLSERVER", SqlServerDialect.class, 11, 12);
	
	private String name; // 必须是全大写
	private Class<? extends Dialect> targetClass;
	private int[] supportDatabaseMajorVersions;
	
	private DialectType(String name, Class<? extends Dialect> targetClass, int... supportDatabaseMajorVersions) {
		this.name = name; // 这里name通过传入的形式, 方便后续扩展同数据库不同版本的枚举
		this.targetClass = targetClass;
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
	 * 当前方言是否支持参数中的数据库
	 * @param key
	 * @return
	 */
	public boolean support(DialectKey key) {
		if(!name.equals(key.getName())) 
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
