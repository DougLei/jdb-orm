package com.douglei.orm.core.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.AbstractBlobDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.Blob;

/**
 * 
 * @author DougLei
 */
public class BlobDataTypeHandler extends AbstractBlobDataTypeHandler{
	private static final long serialVersionUID = -1077470503707245341L;
	private BlobDataTypeHandler() {}
	private static final BlobDataTypeHandler instance = new BlobDataTypeHandler();
	public static final BlobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Blob.singleInstance();
	}
}
