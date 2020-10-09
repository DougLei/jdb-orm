package com.douglei.orm.dialect.impl.sqlserver.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractDatetimeDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Datetime;

/**
 * 
 * @author DougLei
 */
public class DatetimeDataType extends AbstractDatetimeDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return Datetime.getSingleton();
	}
}
