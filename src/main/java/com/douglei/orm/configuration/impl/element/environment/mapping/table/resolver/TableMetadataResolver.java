package com.douglei.orm.configuration.impl.element.environment.mapping.table.resolver;

import org.dom4j.Element;

import com.douglei.orm.configuration.impl.element.environment.mapping.AbstractResolver;
import com.douglei.orm.core.metadata.CreateMode;
import com.douglei.orm.core.metadata.MetadataResolver;
import com.douglei.orm.core.metadata.MetadataResolvingException;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * 表元数据解析
 * @author DougLei
 */
public class TableMetadataResolver extends AbstractResolver implements MetadataResolver<Element, TableMetadata>{

	public TableMetadata resolving(Element element) throws MetadataResolvingException{
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			throw new MetadataResolvingException("<table>元素的name属性值不能为空");
		}
		
		CreateMode createMode = getCreateMode(element);
		return new TableMetadata(name, element.attributeValue("oldName"), element.attributeValue("class"), createMode);
	}
}
