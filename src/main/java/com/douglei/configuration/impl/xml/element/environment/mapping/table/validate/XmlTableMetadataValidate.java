package com.douglei.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;

import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.core.metadata.Metadata;
import com.douglei.core.metadata.MetadataValidate;
import com.douglei.core.metadata.MetadataValidateException;
import com.douglei.core.metadata.table.CreateMode;
import com.douglei.core.metadata.table.TableMetadata;
import com.douglei.utils.StringUtil;

/**
 * 表元数据验证
 * @author DougLei
 */
public class XmlTableMetadataValidate implements MetadataValidate{

	public Metadata doValidate(Object obj) throws MetadataValidateException{
		return doValidate((Element)obj);
	}
	
	private TableMetadata doValidate(Element element) {
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			throw new MetadataValidateException("<table>元素的name属性值不能为空");
		}
		
		CreateMode createMode = getCreateMode(element.attributeValue("createMode"));
		return new TableMetadata(name, element.attributeValue("class"), createMode);
	}

	private CreateMode getCreateMode(String createMode) {
		CreateMode cm = DBRunEnvironmentContext.getTableCreateMode();
		if(cm == null) {
			if(StringUtil.notEmpty(createMode)) {
				cm = CreateMode.toValue(createMode);
			}
			if(cm == null) {
				cm = CreateMode.defaultCreateMode();
			}
		}
		return cm;
	}
}
