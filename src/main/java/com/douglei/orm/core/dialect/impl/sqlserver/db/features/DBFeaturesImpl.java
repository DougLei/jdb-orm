package com.douglei.orm.core.dialect.impl.sqlserver.db.features;

import java.util.HashMap;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.db.features.DBFeatures;

/**
 * 
 * @author DougLei
 */
public class DBFeaturesImpl extends DBFeatures {

	@Override
	public boolean procedureSupportDirectlyReturnResultSet() {
		return true;
	}
	
	@Override
	protected void initSupportColumnDBDataTypeConvertMap() {
		supportColumnDBDataTypeConvertMap = new HashMap<DBDataType, DBDataType[]>(1);
		// TODO SQLSERVER [还未实现] 是否支持改变列的数据类型 从originDataType转换为targetDataType
	}
}
