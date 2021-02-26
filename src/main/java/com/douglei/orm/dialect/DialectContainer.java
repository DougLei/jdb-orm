package com.douglei.orm.dialect;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 方言实例容器
 * @author DougLei
 */
public class DialectContainer {
	private static final List<Dialect> instanceContainer = new ArrayList<Dialect>(DatabaseType.values().length);
	
	/**
	 * 获取方言实例
	 * @param connection
	 * @return
	 * @throws SQLException 
	 */
	public static Dialect get(Connection connection) throws SQLException {
		DatabaseEntity entity = new DatabaseEntity(connection);
		
		if(instanceContainer.size() > 0) {
			for(Dialect dialect : instanceContainer) {
				if(dialect.getDatabaseType().support(entity))
					return dialect;
			}
		}
		
		for(DatabaseType databaseType : DatabaseType.values()) {
			if(databaseType.support(entity)) {
				Dialect dialect = databaseType.getDialectInstance();
				instanceContainer.add(dialect);
				return dialect;
			}
		}
		throw new NullPointerException("框架目前不支持"+entity+"的数据库");
	}
}
