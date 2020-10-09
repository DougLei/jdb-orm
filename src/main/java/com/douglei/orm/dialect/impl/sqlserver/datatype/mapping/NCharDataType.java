package com.douglei.orm.dialect.impl.sqlserver.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNCharDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.NChar;

/**
 * 
 * @author DougLei
 */
public class NCharDataType extends AbstractNCharDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return NChar.getSingleton();
	}
}
