package com.douglei.database.dialect.impl.mysql.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.BlobDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

/**
 * 
 * @author DougLei
 */
public class MySqlBlobDataTypeHandler extends BlobDataTypeHandler{
	private MySqlBlobDataTypeHandler() {}
	private static final MySqlBlobDataTypeHandler instance = new MySqlBlobDataTypeHandler();
	public static final MySqlBlobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return MySqlDBType.BLOB.getSqlType();
	}
}
