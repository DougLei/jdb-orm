package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;

/**
 * 
 * @author DougLei
 */
public class NVarchar2 extends AbstractCharacter{
	
	public NVarchar2() {
		super("NVARCHAR2", -9, 2000);
	}
}
