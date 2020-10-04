package com.douglei.orm.mapping.type;

/**
 * 用来扩展的映射类型
 * @author DougLei
 */
public abstract class MappingType4Extend extends MappingType{

	public MappingType4Extend(String name, String fileSuffix, int priority) {
		super(name, fileSuffix, priority, false, true);
		if(priority <= 60)
			throw new IllegalArgumentException("priority的值, 必须大于60");
	}
}
