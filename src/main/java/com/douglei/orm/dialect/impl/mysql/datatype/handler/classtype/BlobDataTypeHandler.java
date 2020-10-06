package com.douglei.orm.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Mediumblob;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractBlobDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class BlobDataTypeHandler extends AbstractBlobDataTypeHandler{
	private static final long serialVersionUID = 4852491842462309891L;
	private BlobDataTypeHandler() {}
	private static final BlobDataTypeHandler instance = new BlobDataTypeHandler();
	public static final BlobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Mediumblob.singleInstance();
	}
}
