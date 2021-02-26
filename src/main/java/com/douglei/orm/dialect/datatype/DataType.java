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
	public abstract DataTypeClassification getClassification();
	
	/**
	 * 获取类型名称, 全局唯一; 默认使用类的全路径作为name
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
		return "DataType [name=" + getName() + ", classification="+getClassification().name()+"]";
	}
}
