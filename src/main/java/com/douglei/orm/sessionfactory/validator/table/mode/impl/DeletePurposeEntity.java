package com.douglei.orm.sessionfactory.validator.table.mode.impl;

import com.douglei.orm.sessionfactory.validator.table.mode.Mode;
import com.douglei.orm.sessionfactory.validator.table.mode.ExecuteHandler;

/**
 * 
 * @author DougLei
 */
public class DeletePurposeEntity implements ExecuteHandler {
	private static final DeletePurposeEntity singleton = new DeletePurposeEntity();
	public static DeletePurposeEntity getSingleton() {
		return singleton;
	}
	
	private DeletePurposeEntity() {}
	
	@Override
	public Mode getPurpose() {
		return Mode.INSERT;
	}
}
