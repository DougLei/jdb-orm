package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 该数据类型目前只用在处理查询结果集时
 * @author DougLei
 */
public class Numeric extends DBDataType{
	
	public Numeric() {
		super(2, 38, 38);
	}
}
