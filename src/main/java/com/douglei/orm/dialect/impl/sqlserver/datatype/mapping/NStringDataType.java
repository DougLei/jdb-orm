package com.douglei.orm.dialect.impl.sqlserver.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNStringDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.NVarchar;

/**
 * 
 * @author DougLei
 */
public class NStringDataType extends AbstractNStringDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return NVarchar.getSingleton();
	}
}
