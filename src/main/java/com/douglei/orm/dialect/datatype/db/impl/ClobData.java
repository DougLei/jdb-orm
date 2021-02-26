package com.douglei.orm.dialect.datatype.db.impl;

/**
 * 
 * @author DougLei
 */
public class ClobData {
	private String content;
	public ClobData(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return content;
	}
}
