package com.douglei.core.dialect.impl.mysql.db.features;

import com.douglei.core.dialect.db.features.FeaturesHolder;

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