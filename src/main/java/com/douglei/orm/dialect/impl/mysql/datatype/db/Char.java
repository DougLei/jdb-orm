package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractCharacter;

/**
 * 
 * @author DougLei
 */
public class Char extends AbstractCharacter{
	private static final long serialVersionUID = -7471867460847270040L;
	private static final Char singleton = new Char();
	public static Char getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Char() {
		super(1, 255);
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return new Class<?>[] {char.class, Character.class};
	}
}
