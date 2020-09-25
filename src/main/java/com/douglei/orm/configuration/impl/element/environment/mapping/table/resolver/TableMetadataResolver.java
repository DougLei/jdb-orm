package com.douglei.orm.configuration.impl.element.environment.mapping.table.resolver;

import org.dom4j.Element;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.core.metadata.CreateMode;
import com.douglei.orm.core.metadata.MetadataResolver;
import com.douglei.orm.core.metadata.MetadataResolvingException;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * 表元数据解析
 * @author DougLei
 */
public class TableMetadataResolver implements MetadataResolver<Element, TableMetadata>{

	public TableMetadata resolving(Element element) throws MetadataResolvingException{
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			throw new MetadataResolvingException("<table>元素的name属性值不能为空");
		}
		
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
