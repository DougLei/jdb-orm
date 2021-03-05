package com.douglei.orm.dialect.datatype.mapping;

import com.douglei.orm.dialect.datatype.DataType;
import com.douglei.orm.dialect.datatype.DataTypeClassification;

/**
 * 映射的数据类型, 与数据库数据类型的映射
 * @author DougLei
 */
public abstract class MappingDataType extends DataType{
	
	@Override
	public final DataTypeClassification getClassification() {
		return DataTypeClassification.MAPPING;
	}

	/**
	 * 映射的DB数据类型名称
	 * @param length
	 * @param precision
	 * @return
	 */
	public abstract String mappedDBDataType(int length, int precision);
}
