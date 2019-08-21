package com.douglei.orm.core.dialect.impl.sqlserver.db.features;

import java.util.HashMap;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.db.features.DBFeatures;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Int;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Varchar;

/**
 * 
 * @author DougLei
 */
public class DBFeaturesImpl extends DBFeatures {

	@Override
	protected void initSupportColumnDBDataTypeConvertMap() {
		supportColumnDBDataTypeConvertMap = new HashMap<DBDataType, DBDataType[]>(1);
		supportColumnDBDataTypeConvertMap.put(Int.singleInstance(), new DBDataType[] {Varchar.singleInstance()});
	}
}
