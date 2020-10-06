package com.douglei.orm.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Varchar;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractNStringDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class NStringDataTypeHandler extends AbstractNStringDataTypeHandler{
	private static final long serialVersionUID = -178515493848868654L;
	private NStringDataTypeHandler() {}
	private static final NStringDataTypeHandler instance = new NStringDataTypeHandler();
	public static final NStringDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Varchar.singleInstance();
	}
}
