package com.douglei.orm.dialect.impl.oracle.db.features;

import com.douglei.orm.dialect.db.feature.DBFeature;

/**
 * 
 * @author DougLei
 */
public class DBFeaturesImpl extends DBFeature {

	@Override
	public boolean supportProcedureDirectlyReturnResultSet() {
		return false;
	}
}