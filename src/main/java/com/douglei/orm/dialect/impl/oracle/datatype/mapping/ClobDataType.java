package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractClobDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Clob;

/**
 * 
 * @author DougLei
 */
public class ClobDataType extends AbstractClobDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return Clob.getSingleton();
	}
}
