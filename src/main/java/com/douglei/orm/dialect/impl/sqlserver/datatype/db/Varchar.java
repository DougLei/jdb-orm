package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Varchar extends AbstractCharacter{
	private static final long serialVersionUID = 7344138459862108126L;
	private static final Varchar singleton = new Varchar();
	public static Varchar getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Varchar() {
		super(12, 8000);
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
