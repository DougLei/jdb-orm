package com.douglei.database.dialect.datatype.handler.wrapper;

import java.util.Arrays;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(value);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Blob other = (Blob) obj;
		return Arrays.equals(value, other.value);
	}
}
