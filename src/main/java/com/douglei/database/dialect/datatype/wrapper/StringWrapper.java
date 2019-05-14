package com.douglei.database.dialect.datatype.wrapper;

/**
 * 
 * @author DougLei
 */
public abstract class StringWrapper {
	private String value;
	public StringWrapper(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
