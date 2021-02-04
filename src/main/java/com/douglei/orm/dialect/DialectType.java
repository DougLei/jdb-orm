package com.douglei.orm.dialect;

import java.util.Arrays;

import com.douglei.orm.configuration.environment.datasource.DatabaseMetadataEntity;
import com.douglei.orm.dialect.impl.mysql.MySqlDialect;
import com.douglei.orm.dialect.impl.oracle.OracleDialect;
import com.douglei.orm.dialect.impl.sqlserver.SqlServerDialect;
import com.douglei.tools.reflect.ClassUtil;

/**
 * 
 * @author DougLei
 */
public enum DialectType {
	MYSQL("MYSQL", MySqlDialect.class, 8),
	ORACLE("ORACLE", OracleDialect.class, 11),
	SQLSERVER("SQLSERVER", SqlServerDialect.class, 11, 12);
	
	private String name; 
	private Class<? extends Dialect> clazz;
	private int[] supportDatabaseMajorVersions;
	
	private DialectType(String name, Class<? extends Dialect> clazz, int... supportDatabaseMajorVersions) {
		this.name = name.toUpperCase(); // 这里name通过传入的形式, 方便后续扩展同数据库不同版本的枚举
		this.clazz = clazz;
		this.supportDatabaseMajorVersions = supportDatabaseMajorVersions;
	}

	/**
	 * 创建方言实例
	 * @return
	 */
	public Dialect newInstance(){
		return (Dialect) ClassUtil.newInstance(clazz);
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
	 * @param entity
	 * @return
	 */
	public boolean support(DatabaseMetadataEntity entity) {
		if(!name.equals(entity.getName())) 
			return false;
		
		for(int version : supportDatabaseMajorVersions) {
			if(version == entity.getDatabaseMajorVersion())
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return " [name=" + name + ", supportDatabaseMajorVersions=" + Arrays.toString(supportDatabaseMajorVersions) + "] ";
	}
}
