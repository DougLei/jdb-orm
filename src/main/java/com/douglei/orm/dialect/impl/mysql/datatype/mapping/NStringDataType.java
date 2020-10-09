package com.douglei.orm.dialect.impl.mysql.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNStringDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Varchar;

/**
 * 
 * @author DougLei
 */
public class NStringDataType extends AbstractNStringDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return Varchar.getSingleton();
	}
}
