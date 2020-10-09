package com.douglei.orm.dialect.impl.oracle.datatype.mapping;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.impl.AbstractBlobDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Blob;

/**
 * 
 * @author DougLei
 */
public class BlobDataType extends AbstractBlobDataType {

	@Override
	public DBDataType mappedDBDataType(int length, int precision) {
		return Blob.getSingleton();
	}
}
