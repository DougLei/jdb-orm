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
	 * 获取类型名称, 全局唯一
	 * <p>
	 * <b>目前遵守MappingDataType的name都是小写, DBDataType的name都是大写, 自定义的DataType, 使用类的全路径作为name, 即不要重写getName()方法即可</b>
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
