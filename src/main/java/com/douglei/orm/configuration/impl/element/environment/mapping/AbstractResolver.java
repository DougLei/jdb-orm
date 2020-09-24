package com.douglei.orm.configuration.impl.element.environment.mapping;

import org.dom4j.Element;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.core.metadata.CreateMode;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractResolver {
	
	/**
	 * 获取createMode值
	 * @param createMode
	 * @param strict
	 * @return
	 */
	protected CreateMode getCreateMode(Element element) {
		if(Boolean.parseBoolean(element.attributeValue("strict"))) {
			CreateMode createMode = CreateMode.toValue(element.attributeValue("createMode"));
			if(createMode == null)
				return CreateMode.defaultCreateMode();
			return createMode;
		}
		
		CreateMode createMode = EnvironmentContext.getEnvironmentProperty().getTableCreateMode();
		if(createMode == null) {
			createMode = CreateMode.toValue(element.attributeValue("createMode"));
			
			if(createMode == null)
				createMode = CreateMode.defaultCreateMode();
		}
		return createMode;
	}
}
