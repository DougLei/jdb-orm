package com.douglei.orm.mapping.impl.view.metadata.parser;

import org.dom4j.Element;

import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;
import com.douglei.orm.mapping.metadata.parser.MetadataParseException;
import com.douglei.orm.mapping.metadata.parser.MetadataParser;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;
import com.douglei.tools.utils.StringUtil;

/**
 * view元数据解析
 * @author DougLei
 */
public class ViewMetadataParser implements MetadataParser<Element, ViewMetadata>{

	@Override
	public ViewMetadata parse(Element element) throws MetadataParseException{
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) 
			throw new MetadataParseException("<"+elementName()+">元素的name属性值不能为空");
		
		String script = element.getText();
		if(StringUtil.isEmpty(script)) 
			throw new MetadataParseException("<"+elementName()+">元素中的脚本内容不能为空");
		
		return newInstance(name, element.attributeValue("oldName"), script);
	}
	
	/**
	 * 获取元素的name
	 * @return
	 */
	protected String elementName() {
		return MappingTypeNameConstants.VIEW;
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
