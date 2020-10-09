package com.douglei.orm.dialect.impl.sqlserver.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractStringDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Varchar;

/**
 * 
 * @author DougLei
 */
public class StringDataType extends AbstractStringDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return Varchar.getSingleton();
	}
}
