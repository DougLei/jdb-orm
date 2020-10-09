package com.douglei.orm.dialect.impl.sqlserver.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractClobDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Varcharmax;

/**
 * 
 * @author DougLei
 */
public class ClobDataType extends AbstractClobDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return Varcharmax.getSingleton();
	}
}
