package com.douglei.orm.dialect.datatype.db;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.dialect.datatype.DataTypeClassification;
import com.douglei.orm.dialect.datatype.DataType;
import com.douglei.orm.dialect.datatype.mapping.MappingDataType;
import com.douglei.tools.datatype.DataTypeValidateUtil;

/**
 * 数据库数据类型辅助类
 * @author DougLei
 */
public class DBDataTypeUtil {

	/**
	 * 获取数据库数据类型包装类
	 * @param classification 数据类型分类
	 * @param confTypeName 配置的数据类型name
	 * @param confLength 配置的长度
	 * @param confPrecision 配置的精度
	 * @return
	 */
	public static DBDataTypeEntity get(DataTypeClassification classification, String confTypeName, String confLength, String confPrecision) {
		int length = DataTypeValidateUtil.isInteger(confLength)?Integer.parseInt(confLength):0;
		if(length < 0)
			length = 0;
		
		int precision = DataTypeValidateUtil.isInteger(confPrecision)?Integer.parseInt(confPrecision):0;
		if(precision < 0)
			precision = 0;
		
		DataType dataType = EnvironmentContext.getEnvironment().getDialect().getDataTypeContainer().get(classification, confTypeName);
		if(classification == DataTypeClassification.DB) 
			return new DBDataTypeEntity((DBDataType)dataType, length, precision);
		return new DBDataTypeEntity(((MappingDataType)dataType).mappedDBDataType(length, precision), length, precision);
	}
}
