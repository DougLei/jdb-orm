package com.douglei.orm.dialect.impl.oracle.datatype.db;

import com.douglei.orm.dialect.datatype.db.impl.AbstractBlob;

/**
 * 
 * @author DougLei
 */
public class Blob extends AbstractBlob {
	private static final Blob singleton = new Blob();
	public static Blob getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Blob() {
		super("BLOB", 2004);
	}
}
