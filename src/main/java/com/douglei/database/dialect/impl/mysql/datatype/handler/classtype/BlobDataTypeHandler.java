package com.douglei.database.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.DBDataType;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractBlobDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.Mediumblob;

/**
 * 
 * @author DougLei
 */
public class BlobDataTypeHandler extends AbstractBlobDataTypeHandler{
	private BlobDataTypeHandler() {}
	private static final BlobDataTypeHandler instance = new BlobDataTypeHandler();
	public static final BlobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType defaultDBDataType() {
		return Mediumblob.singleInstance();
	}
}
