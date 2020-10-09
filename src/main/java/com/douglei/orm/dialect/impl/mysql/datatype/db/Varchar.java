package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;

/**
 * 
 * @author DougLei
 */
public class Varchar extends AbstractCharacter{
	private static final long serialVersionUID = 7137675695339354125L;
	private static final Varchar singleton = new Varchar();
	public static Varchar getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Varchar() {
		super(12, 1024);
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return new Class<?>[] {String.class};
	}
}
