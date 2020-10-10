package com.douglei.orm.dialect.datatype;

/**
 * 数据类型
 * @author DougLei
 */
public abstract class DataType {
	
	/**
	 * 获取数据类型的分类
	 * @return
	 */
	public abstract Classification getClassification();
	
	/**
	 * 获取类型名称, 全局唯一, 目前遵守MappingDataType的name都是小写, DBDataType的name都是大写
	 * @return
	 */
	public String getName() {
		return getClass().getName();
	}

	@Override
	public final boolean equals(Object obj) {
		if(this == obj)
			return true;
		return this.getName().equals(((DataType)obj).getName());
	}

	@Override
	public String toString() {
		return "DataType [name=" + getName() + "]";
	}
}
