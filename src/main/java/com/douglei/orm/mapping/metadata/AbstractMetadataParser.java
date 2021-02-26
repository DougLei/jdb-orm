package com.douglei.orm.mapping.metadata;

import org.dom4j.Element;

import com.douglei.orm.configuration.environment.CreateMode;
import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractMetadataParser {
	
	/**
	 * 获取name
	 * @param element
	 * @return
	 */
	protected final String getName(Element element) {
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) 
			throw new MetadataParseException("<"+element.getName()+">中的name属性值不能为空");
		
		int maxLength = EnvironmentContext.getEnvironment().getDialect().getDatabaseType().getNameMaxLength();
		if(name.length() > maxLength)
			throw new MetadataParseException("数据库"+element.getName()+"名["+name+"]长度超长, 长度应小于等于"+maxLength);
		return name.toUpperCase();
	}
	
	/**
	 * 获取oldName
	 * @param name
	 * @param element
	 * @return
	 */
	protected final String getOldName(String name, Element element) {
		String oldName = element.attributeValue("oldName");
		if(StringUtil.isEmpty(oldName))
			return name;
		return oldName.toUpperCase();
	}
	
	/**
	 * 获取createMode值
	 * @param createMode
	 * @param strict
	 * @return
	 */
	protected final CreateMode getCreateMode(Element element) {
		if("true".equalsIgnoreCase(element.attributeValue("strict"))) {
			CreateMode createMode = CreateMode.toValue(element.attributeValue("createMode"));
			if(createMode == null)
				return CreateMode.CREATE;
			return createMode;
		}
		
		CreateMode createMode = EnvironmentContext.getEnvironment().getProperty().getGlobalCreateMode();
		if(createMode != null)
			return createMode;
		
		createMode = CreateMode.toValue(element.attributeValue("createMode"));
		return createMode==null?CreateMode.CREATE:createMode;
	}
}
