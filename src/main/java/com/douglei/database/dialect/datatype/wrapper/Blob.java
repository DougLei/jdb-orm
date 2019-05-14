package com.douglei.database.dialect.datatype.wrapper;

/**
 * 
 * @author DougLei
 */
public class Blob {
	private byte[] value;
	public Blob(byte[] value) {
		this.value = value;
	}
	
	public byte[] getValue() {
		return value;
	}
}
