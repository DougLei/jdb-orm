package com.douglei.orm.dialect.impl.mysql.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractClobDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Mediumtext;

/**
 * 
 * @author DougLei
 */
public class ClobDataType extends AbstractClobDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return Mediumtext.getSingleton();
	}
}
