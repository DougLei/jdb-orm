package com.douglei.orm.mapping.impl.table.metadata.parser;

import org.dom4j.Element;

import com.douglei.orm.EnvironmentContext;
import com.douglei.orm.mapping.impl.table.metadata.CreateMode;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.mapping.metadata.parser.MetadataParseException;
import com.douglei.orm.mapping.metadata.parser.MetadataParser;
import com.douglei.tools.utils.StringUtil;

/**
 * 表元数据解析
 * @author DougLei
 */
public class TableMetadataParser implements MetadataParser<Element, TableMetadata>{

	@Override
	public TableMetadata parse(Element element) throws MetadataParseException{
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) 
			throw new MetadataParseException("<table>元素的name属性值不能为空");
		
		CreateMode createMode = getCreateMode(element);
		return new TableMetadata(name, element.attributeValue("oldName"), element.attributeValue("class"), createMode);
	}
	
	/**
	 * 获取createMode值
	 * @param createMode
	 * @param strict
	 * @return
	 */
	private CreateMode getCreateMode(Element element) {
		if(Boolean.parseBoolean(element.attributeValue("strict"))) {
			CreateMode createMode = CreateMode.toValue(element.attributeValue("createMode"));
			if(createMode == null)
				return CreateMode.defaultCreateMode();
			return createMode;
		}
		
		CreateMode createMode = EnvironmentContext.getProperty().getTableCreateMode();
		if(createMode == null) {
			createMode = CreateMode.toValue(element.attributeValue("createMode"));
			
			if(createMode == null)
				createMode = CreateMode.defaultCreateMode();
		}
		return createMode;
	}
}
