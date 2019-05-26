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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		StringWrapper other = (StringWrapper) obj;
		if (value == null) {
			if(other.value == null) {
				return true;
			}
			return false;
		}
		return value.equals(other.value);
	}
}
