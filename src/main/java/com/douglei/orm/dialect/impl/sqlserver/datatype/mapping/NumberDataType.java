package com.douglei.orm.dialect.impl.sqlserver.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractNumberDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Bigint;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Decimal;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Int;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Smallint;

/**
 * 
 * @author DougLei
 */
public class NumberDataType extends AbstractNumberDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		if(precision > 0)
			return Decimal.getSingleton();
		if(length > 0 && length < 6)
			return Smallint.getSingleton();
		if(length > 10)
			return Bigint.getSingleton();
		return Int.getSingleton();
	}
}
