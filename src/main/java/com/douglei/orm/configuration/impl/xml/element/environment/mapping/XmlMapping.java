package com.douglei.orm.configuration.impl.xml.element.environment.mapping;

import com.douglei.orm.configuration.environment.mapping.Mapping;

/**
 * 映射的抽象父类
 * @author DougLei
 */
public abstract class XmlMapping implements Mapping{
	protected String configFileName;

	public XmlMapping(String configFileName) {
		this.configFileName = configFileName;
	}
}