package com.douglei.database.metadata.table.column.extend;

/**
 * 
 * @author DougLei
 */
public enum ConstraintType {
	A;
	
	public static ConstraintType toValue(String type) {
		type = type.toUpperCase();
		ConstraintType[] cts = ConstraintType.values();
		for (ConstraintType ct : cts) {
			if(ct.name().equals(type)) {
				return ct;
			}
		}
		return null;
	}
}
