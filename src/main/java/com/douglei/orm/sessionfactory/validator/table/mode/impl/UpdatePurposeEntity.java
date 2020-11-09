package com.douglei.orm.sessionfactory.validator.table.mode.impl;

import com.douglei.orm.sessionfactory.validator.table.mode.Mode;
import com.douglei.orm.sessionfactory.validator.table.mode.ExecuteHandler;

/**
 * 
 * @author DougLei
 */
public class UpdatePurposeEntity implements ExecuteHandler {
	private boolean updateNullValue;
	public static final UpdatePurposeEntity DEFAULT = new UpdatePurposeEntity(false);
	
	/**
	 * 
	 * @param updateNullValue 是否更新null值, 如果要更新null值, 则会对null值进行验证; 
	 */
	public UpdatePurposeEntity(boolean updateNullValue) {
		this.updateNullValue = updateNullValue;
	}

	@Override
	public Mode getPurpose() {
		return Mode.INSERT;
	}
}
