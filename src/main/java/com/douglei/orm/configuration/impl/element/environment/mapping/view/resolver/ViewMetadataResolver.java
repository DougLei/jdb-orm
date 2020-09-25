package com.douglei.orm.configuration.impl.element.environment.mapping.view.resolver;

import org.dom4j.Element;

import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.core.metadata.MetadataResolver;
import com.douglei.orm.core.metadata.MetadataResolvingException;
import com.douglei.orm.core.metadata.view.ViewMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * view元数据解析
 * @author DougLei
 */
public class ViewMetadataResolver implements MetadataResolver<Element, ViewMetadata>{

	public ViewMetadata resolving(Element element) throws MetadataResolvingException{
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) 
			throw new MetadataResolvingException("<"+elementName()+">元素的name属性值不能为空");
		
		String script = element.getText();
		if(StringUtil.isEmpty(script)) 
			throw new MetadataResolvingException("<"+elementName()+">元素中的脚本内容不能为空");
		
		return newInstance(name, element.attributeValue("oldName"), script);
	}
	
	/**
	 * 获取元素的name
	 * @return
	 */
	protected String elementName() {
		return MappingType.VIEW.getName();
	}
	
	/**
	 * 创建实例
	 * @param name
	 * @param oldName
	 * @param createMode
	 * @param script
	 * @return
	 */
	protected ViewMetadata newInstance(String name, String oldName, String script) {
		return new ViewMetadata(name, oldName, script); 
	}
}
