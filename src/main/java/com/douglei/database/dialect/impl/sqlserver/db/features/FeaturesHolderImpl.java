package com.douglei.database.dialect.impl.sqlserver.db.features;

import com.douglei.database.dialect.db.features.FeaturesHolder;

/**
 * 
 * @author DougLei
 */
public class FeaturesHolderImpl implements FeaturesHolder {

	@Override
	public boolean procedureSupportDirectlyReturnResultSet() {
		return true;
	}
}
