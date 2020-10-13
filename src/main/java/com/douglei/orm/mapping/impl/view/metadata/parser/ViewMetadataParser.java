package com.douglei.orm.mapping.impl.view.metadata.parser;

import org.dom4j.Element;

import com.douglei.orm.mapping.impl.table.metadata.CreateMode;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;
import com.douglei.orm.mapping.metadata.parser.MetadataParseException;
import com.douglei.orm.mapping.metadata.parser.MetadataParser;
import com.douglei.orm.mapping.type.MappingTypeConstants;
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
		
		CreateMode createMode = getCreateMode(element);
		// 这里不用专门判断none的情况, 因为在后续处理中, create和none都在switch的default中了
		if(createMode == CreateMode.DYNAMIC_UPDATE)
			createMode = CreateMode.DROP_CREATE;
		
		return newInstance(name, element.attributeValue("oldName"), createMode, script);
	}
	
	/**
	 * 获取元素的name
	 * @return
	 */
	protected String elementName() {
		return MappingTypeConstants.VIEW;
	}
	
	/**
	 * 创建实例
	 * @param name
	 * @param oldName
	 * @param createMode
	 * @param script
	 * @return
	 */
	protected ViewMetadata newInstance(String name, String oldName, CreateMode createMode, String script) {
		return new ViewMetadata(name, oldName, createMode, script); 
	}
}
