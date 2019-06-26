package com.douglei.orm.core.dialect.impl.oracle.db.features;

import java.util.HashMap;

import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.dialect.db.features.DBFeatures;

/**
 * 
 * @author DougLei
 */
public class DBFeaturesImpl extends DBFeatures {

	@Override
	public boolean procedureSupportDirectlyReturnResultSet() {
		return false;
	}

	@Override
	protected void initSupportColumnDataTypeConvertMap() {
		supportColumnDataTypeConvertMap = new HashMap<DataType, DataType[]>(DataType.values().length);
		// TODO ORACLE [还未实现] 是否支持改变列的数据类型 从originDataType转换为targetDataType
	}
}
