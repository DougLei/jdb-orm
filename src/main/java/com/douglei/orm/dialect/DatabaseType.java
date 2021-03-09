package com.douglei.orm.dialect;

import java.util.Arrays;

import com.douglei.orm.dialect.impl.mysql.MySqlDialect;
import com.douglei.orm.dialect.impl.oracle.OracleDialect;
import com.douglei.orm.dialect.impl.sqlserver.SqlServerDialect;
import com.douglei.tools.reflect.ClassUtil;

/**
 * 
 * @author DougLei
 */
public enum DatabaseType {
	
	MYSQL(DatabaseNameConstants.MYSQL, 64, true, MySqlDialect.class, 8),
	
	ORACLE(DatabaseNameConstants.ORACLE, 30, false, OracleDialect.class, 11),
	
	SQLSERVER(DatabaseNameConstants.SQLSERVER, 128, true, SqlServerDialect.class, 11, 12);
	
	private String name; // 数据库名
	private int nameMaxLength; // 数据库对象名的最大长度
	private boolean supportProcedureDirectlyReturnResultSet; // 是否支持存储过程直接返回结果集
	private Class<? extends Dialect> dialectClass; // 对应的方言类
	private int[] supportDatabaseMajorVersions; // 支持的数据库主版本数组
	
	private DatabaseType(String name, int nameMaxLength, boolean supportProcedureDirectlyReturnResultSet, Class<? extends Dialect> dialectClass, int... supportDatabaseMajorVersions) {
		this.name = name.toUpperCase(); // 这里name通过传入的形式, 方便后续扩展同名但不同版本的数据库枚举
		this.dialectClass = dialectClass;
		this.nameMaxLength = nameMaxLength;
		this.supportDatabaseMajorVersions = supportDatabaseMajorVersions;
	}

	public String getName(){
		return name;
	}
	public int getNameMaxLength() {
		return nameMaxLength;
	}
	public boolean supportProcedureDirectlyReturnResultSet() {
		return supportProcedureDirectlyReturnResultSet;
	}
	public Dialect getDialectInstance(){
		return (Dialect) ClassUtil.newInstance(dialectClass);
	}
	
	/**
	 * 是否支持指定的数据库
	 * @param entity
	 * @return
	 */
	public boolean support(DatabaseEntity entity) {
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
		return "DatabaseType [name=" + name + ", versions=" + Arrays.toString(supportDatabaseMajorVersions) + "]";
	}
}
