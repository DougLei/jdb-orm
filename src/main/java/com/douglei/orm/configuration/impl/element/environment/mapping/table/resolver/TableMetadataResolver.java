package com.douglei.orm.configuration.impl.element.environment.mapping.table.resolver;

import org.dom4j.Element;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.core.metadata.MetadataResolver;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.table.CreateMode;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * 表元数据解析
 * @author DougLei
 */
public class TableMetadataResolver implements MetadataResolver<Element, TableMetadata>{

	public TableMetadata resolving(Element element) throws MetadataValidateException{
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			throw new MetadataValidateException("<table>元素的name属性值不能为空");
		}
		
		CreateMode createMode = getCreateMode(element.attributeValue("createMode"), element.attributeValue("forceCreateMode"));
		return new TableMetadata(name, element.attributeValue("oldName"), element.attributeValue("class"), createMode);
	}

	private CreateMode getCreateMode(String createMode, String forceCreateMode) {
		CreateMode cm = null;
		if(StringUtil.isEmpty(forceCreateMode)) {
			if((cm = EnvironmentContext.getEnvironmentProperty().getTableCreateMode()) == null) {
				cm = CreateMode.toValue(createMode);
			}
		}else {
			cm = CreateMode.toValue(forceCreateMode);
		}
		if(cm == null) {
			cm = CreateMode.defaultCreateMode();
		}
		return cm;
	}
}
