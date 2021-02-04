package com.douglei.orm.mapping.metadata;

import com.douglei.orm.configuration.environment.EnvironmentContext;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDatabaseObjectMetadata extends AbstractMetadata{
	private static final long serialVersionUID = 8123763182751496523L;

	protected AbstractDatabaseObjectMetadata(String name) {
		EnvironmentContext.getDialect().getObjectHandler().validateObjectName(name); // 验证name的长度是否超过数据库支持的最大长度
	}
}
