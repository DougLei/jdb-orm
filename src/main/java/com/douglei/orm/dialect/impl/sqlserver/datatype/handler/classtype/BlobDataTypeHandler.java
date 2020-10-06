package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Varbinary;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractBlobDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class BlobDataTypeHandler extends AbstractBlobDataTypeHandler{
	private static final long serialVersionUID = -7889963192544844583L;
	private BlobDataTypeHandler() {}
	private static final BlobDataTypeHandler instance = new BlobDataTypeHandler();
	public static final BlobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Varbinary.singleInstance();
	}
}
