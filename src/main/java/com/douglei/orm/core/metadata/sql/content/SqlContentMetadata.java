package com.douglei.orm.core.metadata.sql.content;

/**
 * sql-content元数据
 * @author DougLei
 */
public class SqlContentMetadata extends ContentMetadata{
	private static final long serialVersionUID = 4309044799719776150L;

	public SqlContentMetadata(String name, IncrementIdValueConfig incrementIdValueConfig) {
		super(name, incrementIdValueConfig);
	}
}
