package com.douglei.orm.mapping.impl.view.metadata;

import org.dom4j.Element;

import com.douglei.orm.configuration.environment.CreateMode;
import com.douglei.orm.mapping.metadata.AbstractMetadataParser;
import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.tools.StringUtil;

/**
 * view元数据解析
 * @author DougLei
 */
public class ViewMetadataParser extends AbstractMetadataParser {

	/**
	 * 解析ViewMetadata
	 * @param element
	 * @return
	 * @throws MetadataParseException
	 */
	public ViewMetadata parse(Element element) throws MetadataParseException{
		// 解析name和oldName
		String name = getName(element);
		String oldName = getOldName(name, element);
		
		// 解析脚本
		String script = element.getText();
		if(StringUtil.isEmpty(script)) 
			throw new MetadataParseException("<"+element.getName()+">中的脚本内容不能为空");
		
		CreateMode createMode = getCreateMode(element);
		if(createMode != CreateMode.NONE && createMode != CreateMode.DROP_CREATE)
			createMode = CreateMode.DROP_CREATE;
		
		return newInstance(name, oldName, createMode, script);
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
