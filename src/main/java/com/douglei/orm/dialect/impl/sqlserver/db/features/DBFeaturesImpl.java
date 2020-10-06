package com.douglei.orm.dialect.impl.sqlserver.db.features;

import java.util.HashMap;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.feature.DBFeature;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Int;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Varchar;

/**
 * 
 * @author DougLei
 */
public class DBFeaturesImpl extends DBFeature {

	@Override
	protected void initSupportColumnDBDataTypeConvertMap() {
		supportColumnDBDataTypeConvertMap = new HashMap<DBDataType, DBDataType[]>(1);
		supportColumnDBDataTypeConvertMap.put(Int.singleInstance(), new DBDataType[] {Varchar.singleInstance()});
	}
}
