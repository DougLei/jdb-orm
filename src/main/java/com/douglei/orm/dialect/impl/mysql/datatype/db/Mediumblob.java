package com.douglei.orm.dialect.impl.mysql.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractBlob;

/**
 * 
 * @author DougLei
 */
public class Mediumblob extends AbstractBlob{
	private static final long serialVersionUID = -3399145019814856406L;
	private static final Mediumblob singleton = new Mediumblob();
	public static Mediumblob getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Mediumblob() {
		super(-4);
	}
}
