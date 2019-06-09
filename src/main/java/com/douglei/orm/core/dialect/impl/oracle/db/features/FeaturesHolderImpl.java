package com.douglei.orm.core.dialect.impl.oracle.db.features;

import com.douglei.orm.core.dialect.db.features.FeaturesHolder;

/**
 * 
 * @author DougLei
 */
public class FeaturesHolderImpl implements FeaturesHolder {

	@Override
	public boolean procedureSupportDirectlyReturnResultSet() {
		return false;
	}
}
