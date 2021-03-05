package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Char extends AbstractCharacter{
	
	public Char() {
		super("CHAR", 1, 8000);
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return new Class<?>[] {char.class, Character.class};
	}
	
	@Override
	protected int calcLength(String string) {
		return StringUtil.calcLength(string);
	}
}
