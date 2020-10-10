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
	 * 获取类型名称, 全局唯一, 要求名称必须全部大写
	 * @return
	 */
	public String getName() {
		return getClass().getName().toUpperCase();
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
