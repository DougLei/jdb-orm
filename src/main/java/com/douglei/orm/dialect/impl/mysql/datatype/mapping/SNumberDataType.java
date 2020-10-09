package com.douglei.orm.dialect.impl.mysql.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractSNumberDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Bigint;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Decimal;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Int;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Smallint;

/**
 * 
 * @author DougLei
 */
public class SNumberDataType extends AbstractSNumberDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		if(precision > 0)
			return Decimal.getSingleton();
		if(length > 0 && length < 5)
			return Smallint.getSingleton();
		if(length > 9)
			return Bigint.getSingleton();
		return Int.getSingleton();
	}
}
