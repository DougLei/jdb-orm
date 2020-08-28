package com.douglei.orm.configuration.impl.element.environment.mapping;

import com.douglei.orm.configuration.environment.mapping.Mapping;

/**
 * 映射的抽象父类
 * @author DougLei
 */
public abstract class MappingImpl implements Mapping{
	private static final long serialVersionUID = -4443025203963005833L;
	
	protected String configDescription;

	public MappingImpl(String configDescription) {
		this.configDescription = configDescription;
	}
}
