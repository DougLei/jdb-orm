package com.douglei.orm.dialect.impl.sqlserver.dbobject.pk.sequence;

import com.douglei.orm.dialect.dbobject.pk.sequence.PrimaryKeySequence;

/**
 * sqlserver主键序列
 * @author DougLei
 */
public class SqlServerPrimaryKeySequence extends PrimaryKeySequence{
	private static final SqlServerPrimaryKeySequence singleton = new SqlServerPrimaryKeySequence();
	public static PrimaryKeySequence getSingleton() {
		return singleton;
	}
	
	private SqlServerPrimaryKeySequence() {
	}
	
	// 在反序列化时, 防止单例模式被破坏
	public Object readResolve() {
		return singleton;
	}
}
