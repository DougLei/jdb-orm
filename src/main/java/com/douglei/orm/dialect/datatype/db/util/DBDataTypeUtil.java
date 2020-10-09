package com.douglei.orm.dialect.datatype.db.util;

import com.douglei.orm.EnvironmentContext;
import com.douglei.orm.dialect.datatype.Classification;
import com.douglei.orm.dialect.datatype.DataType;
import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.mapping.MappingDataType;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 数据库数据类型辅助类
 * @author DougLei
 */
public class DBDataTypeUtil {

	/**
	 * 获取数据库数据类型包装类
	 * @param confLengthVal 配置的长度值
	 * @param confPrecisionVal 配置的精度值
	 * @param confDataTypeVal 配置的数据类型
	 * @return
	 */
	public static DBDataTypeWrapper get(String confLengthVal, String confPrecisionVal, String confDataTypeVal) {
		int length = VerifyTypeMatchUtil.isInteger(confLengthVal)?Integer.parseInt(confLengthVal):0;
		int precision = VerifyTypeMatchUtil.isInteger(confPrecisionVal)?Integer.parseInt(confPrecisionVal):0;
		
		DataType dataType = EnvironmentContext.getDialect().getDataTypeContainer().get(confDataTypeVal);
		if(dataType.getClassification() == Classification.DB) 
			return new DBDataTypeWrapper((DBDataType)dataType, length, precision);
		return new DBDataTypeWrapper(((MappingDataType)dataType).mappedDBDataType(length, precision), length, precision);
	}
}