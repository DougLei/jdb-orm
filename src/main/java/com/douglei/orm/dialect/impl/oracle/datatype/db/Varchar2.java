package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Varchar2 extends AbstractCharacter{
	private static final Varchar2 singleton = new Varchar2();
	public static Varchar2 getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Varchar2() {
		super(12, 4000);
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
