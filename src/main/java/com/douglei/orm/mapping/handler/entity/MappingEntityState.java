package com.douglei.orm.mapping.handler.entity;

/**
 * 
 * @author DougLei
 */
public class MappingEntityState {
	private String code;
	private String type;
	private boolean builtin;
	
	public MappingEntityState(String code, String type, boolean builtin) {
		this.code = code;
		this.type = type;
		this.builtin = builtin;
	}

	public String getCode() {
		return code;
	}
	public String getType() {
		return type;
	}
	public boolean isBuiltin() {
		return builtin;
	}
}
