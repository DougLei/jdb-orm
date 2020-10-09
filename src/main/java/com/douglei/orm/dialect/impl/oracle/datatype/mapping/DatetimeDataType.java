package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractDatetimeDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Date;

/**
 * 
 * @author DougLei
 */
public class DatetimeDataType extends AbstractDatetimeDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return Date.getSingleton();
	}
}
