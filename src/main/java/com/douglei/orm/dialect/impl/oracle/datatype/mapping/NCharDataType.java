package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNCharDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.NChar;

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
