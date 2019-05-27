package com.douglei.database.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.handler.classtype.AbstractBlobDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.SqlServerDBType;

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
	protected int getSqlType() {
		return SqlServerDBType.VARBINARY.getSqlType();
	}
}
