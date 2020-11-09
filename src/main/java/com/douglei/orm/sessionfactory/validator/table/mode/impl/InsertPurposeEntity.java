package com.douglei.orm.sessionfactory.validator.table.mode.impl;

import com.douglei.orm.sessionfactory.validator.table.mode.Mode;
import com.douglei.orm.sessionfactory.validator.table.mode.ExecuteHandler;

/**
 * 
 * @author DougLei
 */
public class InsertPurposeEntity implements ExecuteHandler {
	private static final InsertPurposeEntity singleton = new InsertPurposeEntity();
	public static InsertPurposeEntity getSingleton() {
		return singleton;
	}
	
	private InsertPurposeEntity() {}
	
	@Override
	public Mode getPurpose() {
		return Mode.INSERT;
	}
}
