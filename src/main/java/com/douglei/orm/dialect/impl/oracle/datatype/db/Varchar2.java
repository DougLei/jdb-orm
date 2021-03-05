package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Varchar2 extends AbstractCharacter{
	
	public Varchar2() {
		super("VARCHAR2", 12, 4000);
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return new Class<?>[] {String.class};
	}
	
	@Override
	protected int calcLength(String string) {
		return StringUtil.calcLength(string);
	}
}
