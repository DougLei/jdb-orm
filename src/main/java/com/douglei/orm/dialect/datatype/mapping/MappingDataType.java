package com.douglei.orm.dialect.datatype.mapping;

import com.douglei.orm.dialect.datatype.Classification;
import com.douglei.orm.dialect.datatype.DataType;
import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 映射的数据类型, 与数据库数据类型的映射
 * @author DougLei
 */
public abstract class MappingDataType extends DataType{
	public static final String DEFAULT_NAME = "string";
	
	@Override
	public final Classification getClassification() {
		return Classification.MAPPING;
	}

	/**
	 * 映射的数据库数据类型
	 * @return
	 */
	public abstract DBDataType mappedDBDataType();
}
