package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;

/**
 * 
 * @author DougLei
 */
public class NVarchar extends AbstractCharacter{
	
	public NVarchar() {
		super("NVARCHAR", -9, 4000);
	}
}
