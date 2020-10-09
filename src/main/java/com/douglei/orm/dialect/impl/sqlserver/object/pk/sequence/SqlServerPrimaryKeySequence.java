package com.douglei.orm.dialect.impl.sqlserver.object.pk.sequence;

import com.douglei.orm.dialect.object.pk.sequence.PrimaryKeySequence;

/**
 * sqlserver主键序列
 * @author DougLei
 */
public class SqlServerPrimaryKeySequence extends PrimaryKeySequence{
	private static final SqlServerPrimaryKeySequence singleton = new SqlServerPrimaryKeySequence();
	public static PrimaryKeySequence getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private SqlServerPrimaryKeySequence() {
	}
	
	@Override
	public boolean executeSql() {
		return false;
	}
}
