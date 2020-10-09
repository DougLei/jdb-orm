package com.douglei.orm.dialect.impl.sqlserver.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractCharDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Char;

/**
 * 
 * @author DougLei
 */
public class CharDataType extends AbstractCharDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return Char.getSingleton();
	}
}
