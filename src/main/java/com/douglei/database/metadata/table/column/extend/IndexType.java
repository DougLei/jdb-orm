package com.douglei.database.metadata.table.column.extend;

/**
 * 
 * @author DougLei
 */
public enum IndexType {
	A;
	
	public static IndexType toValue(String type) {
		type = type.toUpperCase();
		IndexType[] its = IndexType.values();
		for (IndexType it : its) {
			if(it.name().equals(type)) {
				return it;
			}
		}
		return null;
	}
}
