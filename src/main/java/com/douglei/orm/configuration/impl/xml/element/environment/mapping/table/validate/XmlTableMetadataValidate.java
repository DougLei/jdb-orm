package com.douglei.orm.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.metadata.MetadataValidate;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.table.CreateMode;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * 表元数据验证
 * @author DougLei
 */
public class XmlTableMetadataValidate implements MetadataValidate<Element, TableMetadata>{

	public TableMetadata doValidate(Element element) throws MetadataValidateException{
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			throw new MetadataValidateException("<table>元素的name属性值不能为空");
		}
		
		CreateMode createMode = getCreateMode(element.attributeValue("createMode"));
		return new TableMetadata(name, element.attributeValue("oldName"), element.attributeValue("class"), createMode);
	}

	private CreateMode getCreateMode(String createMode) {
		CreateMode cm = DBRunEnvironmentContext.getEnvironmentProperty().getTableCreateMode();
		if(cm == null) {
			if(StringUtil.notEmpty(createMode)) {
				cm = CreateMode.toValue(createMode);
			}
			if(cm == null) {
				cm = CreateMode.defaultCreateMode();
			}
		}
		if(cm == CreateMode.DYNAMIC_UPDATE && !DBRunEnvironmentContext.getEnvironmentProperty().getEnableTableDynamicUpdate()) {
			cm = CreateMode.defaultCreateMode();
		}
		return cm;
	}
}
