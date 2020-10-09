package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNumberDataType;

/**
 * 
 * @author DougLei
 */
public class NumberDataType extends AbstractNumberDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return com.douglei.orm.dialect.impl.oracle.datatype.db.Number.getSingleton();
	}
}
