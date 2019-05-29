package com.douglei.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;

import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.table.CreateMode;
import com.douglei.database.metadata.table.TableMetadata;
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
		DBRunEnvironmentContext.getDialect().getDBObjectNameHandler().validateDBObjectName(name);
		
		CreateMode createMode = getCreateMode(element.attributeValue("createMode"));
		return new TableMetadata(name, element.attributeValue("class"), createMode);
	}

	private CreateMode getCreateMode(String createMode) {
		CreateMode cm = null;
		if(StringUtil.notEmpty(createMode)) {
			cm = CreateMode.toValue(createMode);
		}
		if(cm == null) {
			cm = DBRunEnvironmentContext.getTableCreateMode();
		}
		return cm;
	}
}
