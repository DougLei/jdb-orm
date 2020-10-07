package com.douglei.orm.dialect.datatype.mapping;

import com.douglei.orm.dialect.datatype.Classification;
import com.douglei.orm.dialect.datatype.DataType;
import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 映射的数据类型, 与数据库数据类型的映射
 * @author DougLei
 */
public abstract class MappingDataType extends DataType{
	
	@Override
	public final Classification getClassification() {
		return Classification.MAPPING;
	}

	/**
	 * 映射的数据库数据类型, 部分映射类型会需要根据长度和精度决定最终返回哪种数据库数据类型, 例如number类型
	 * @param length
	 * @param precision
	 * @return
	 */
	public abstract DBDataType mappedDBDataType(int length, int precision);
}
