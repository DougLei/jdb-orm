package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractStringDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Varchar2;

/**
 * 
 * @author DougLei
 */
public class StringDataType extends AbstractStringDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return Varchar2.getSingleton();
	}
}
