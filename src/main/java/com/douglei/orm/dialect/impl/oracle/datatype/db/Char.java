package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Char extends AbstractCharacter{
	
	public Char() {
		super("CHAR", 1, 2000);
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
