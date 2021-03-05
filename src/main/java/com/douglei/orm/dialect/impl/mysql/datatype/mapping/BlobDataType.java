package com.douglei.orm.dialect.impl.mysql.datatype.mapping;

import com.douglei.orm.dialect.datatype.mapping.impl.AbstractBlobDataType;

/**
 * 
 * @author DougLei
 */
public class BlobDataType extends AbstractBlobDataType {

	@Override
	public String mappedDBDataType(int length, int precision) {
		return "MEDIUMBLOB";
	}
}
