package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.BlobDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;

/**
 * 
 * @author DougLei
 */
public class OracleBlobDataTypeHandler extends BlobDataTypeHandler{
	private OracleBlobDataTypeHandler() {}
	private static final OracleBlobDataTypeHandler instance = new OracleBlobDataTypeHandler();
	public static final OracleBlobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return OracleDBType.BLOB.getSqlType();
	}
}
