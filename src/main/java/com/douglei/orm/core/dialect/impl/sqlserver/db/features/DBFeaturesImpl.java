package com.douglei.orm.core.dialect.impl.sqlserver.db.features;

import com.douglei.orm.core.dialect.db.features.DBFeatures;

/**
 * 
 * @author DougLei
 */
public class DBFeaturesImpl implements DBFeatures {

	@Override
	public boolean procedureSupportDirectlyReturnResultSet() {
		return true;
	}
}