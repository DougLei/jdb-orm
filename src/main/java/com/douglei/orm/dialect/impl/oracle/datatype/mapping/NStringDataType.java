package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNStringDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.NVarchar2;

/**
 * 
 * @author DougLei
 */
public class NStringDataType extends AbstractNStringDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return NVarchar2.getSingleton();
	}
}
