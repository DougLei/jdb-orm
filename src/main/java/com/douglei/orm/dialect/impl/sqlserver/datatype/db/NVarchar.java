package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;

/**
 * 
 * @author DougLei
 */
public class NVarchar extends AbstractCharacter{
	private static final long serialVersionUID = -3035394731433852219L;
	private static final NVarchar singleton = new NVarchar();
	public static NVarchar getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private NVarchar() {
		super(-9, 4000);
	}
}
