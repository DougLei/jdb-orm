package com.douglei.orm.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;

import com.douglei.orm.context.EnvironmentContext;
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
		
		if(cm == CreateMode.DYNAMIC_UPDATE && !EnvironmentContext.getEnvironmentProperty().enableTableDynamicUpdate()) {
			cm = CreateMode.defaultCreateMode();
		}
		return cm;
	}
}
