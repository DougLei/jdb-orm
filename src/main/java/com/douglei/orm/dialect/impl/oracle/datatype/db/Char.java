package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Char extends AbstractCharacter{
	private static final long serialVersionUID = 2585808564186932358L;
	private static final Char singleton = new Char();
	public static Char getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Char() {
		super(1, 2000);
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
