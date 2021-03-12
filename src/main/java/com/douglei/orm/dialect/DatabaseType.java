package com.douglei.orm.dialect;

import java.util.Arrays;

import com.douglei.orm.dialect.impl.mysql.MySqlDialect;
import com.douglei.orm.dialect.impl.oracle.OracleDialect;
import com.douglei.orm.dialect.impl.sqlserver.SqlServerDialect;

/**
 * 
 * @author DougLei
 */
public enum DatabaseType {
	
	MYSQL(DatabaseNameConstants.MYSQL, 8) {

		@Override
		public int getNameMaxLength() {
			return 64;
		}

		@Override
		public boolean supportProcedureDirectlyReturnResultSet() {
			return true;
		}

		@Override
		public boolean extractOrderByClause() {
			return false;
		}

		@Override
		public Dialect getDialectInstance() {
			return new MySqlDialect();
		}
		
	},
	
	ORACLE(DatabaseNameConstants.ORACLE, 11) {

		@Override
		public int getNameMaxLength() {
			return 30;
		}

		@Override
		public boolean supportProcedureDirectlyReturnResultSet() {
			return false;
		}

		@Override
		public boolean extractOrderByClause() {
			return false;
		}

		@Override
		public Dialect getDialectInstance() {
			return new OracleDialect();
		}
		
	},
	
	SQLSERVER(DatabaseNameConstants.SQLSERVER, 11, 12) {

		@Override
		public int getNameMaxLength() {
			return 128;
		}

		@Override
		public boolean supportProcedureDirectlyReturnResultSet() {
			return true;
		}

		@Override
		public boolean extractOrderByClause() {
			return true;
		}

		@Override
		public Dialect getDialectInstance() {
			return new SqlServerDialect();
		}
		
	};
	
	
	private String name; // 数据库名
	private int[] supportDatabaseMajorVersions; // 支持的数据库主版本数组
	private DatabaseType(String name, int... supportDatabaseMajorVersions) {
		this.name = name; // 这里name通过传入的形式, 方便后续扩展同名但不同版本的数据库枚举
		this.supportDatabaseMajorVersions = supportDatabaseMajorVersions;
	}

	/**
	 * 获取数据库类型的名称
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 获取数据库对象名的最大长度
	 * @return
	 */
	public abstract int getNameMaxLength();
	
	/**
	 * 是否支持存储过程直接返回结果集
	 * @return
	 */
	public abstract boolean supportProcedureDirectlyReturnResultSet();
	
	/**
	 * select语句时, 是否需要提取(最外层的)order by子句
	 * @return
	 */
	public abstract boolean extractOrderByClause();
	
	/**
	 * 获取Dialect实例
	 * @return
	 */
	public abstract Dialect getDialectInstance();
	
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
