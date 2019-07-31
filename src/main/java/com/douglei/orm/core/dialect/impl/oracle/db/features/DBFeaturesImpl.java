package com.douglei.orm.core.dialect.impl.oracle.db.features;

import com.douglei.orm.core.dialect.db.features.DBFeatures;

/**
 * 
 * @author DougLei
 */
public class DBFeaturesImpl extends DBFeatures {

	@Override
	public boolean needCreatePrimaryKeySequence() {
		return true;
	}

	@Override
	public boolean supportProcedureDirectlyReturnResultSet() {
		return false;
	}
}
