package com.douglei.orm.dialect.impl.mysql.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractBlobDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Mediumblob;

/**
 * 
 * @author DougLei
 */
public class BlobDataType extends AbstractBlobDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return Mediumblob.getSingleton();
	}
}
