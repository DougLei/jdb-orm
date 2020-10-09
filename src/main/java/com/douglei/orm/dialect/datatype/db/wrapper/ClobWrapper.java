package com.douglei.orm.dialect.datatype.db.wrapper;

/**
 * 
 * @author DougLei
 */
public class ClobWrapper {
	private String content;

	public ClobWrapper(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return content;
	}
}
