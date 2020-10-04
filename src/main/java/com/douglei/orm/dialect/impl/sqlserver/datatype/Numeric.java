package com.douglei.orm.dialect.impl.sqlserver.datatype;

import com.douglei.orm.dialect.datatype.DBDataType;

/**
 * 该数据类型目前只用在处理查询结果集时
 * @author DougLei
 */
public class Numeric extends DBDataType{
	private static final long serialVersionUID = 5394760288638623365L;
	private static final Numeric instance = new Numeric();
	public static final Numeric singleInstance() {
		return instance;
	}
	
	private Numeric() {
		super((short)2, (short)38, (short)38);
	}
}
