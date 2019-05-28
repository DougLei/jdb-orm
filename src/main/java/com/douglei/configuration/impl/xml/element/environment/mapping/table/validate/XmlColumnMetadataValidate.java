package com.douglei.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.impl.xml.element.environment.mapping.table.CurrentTableIsCreate;
import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.database.dialect.datatype.handler.DataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractStringDataTypeHandler;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.utils.StringUtil;

/**
 * 列元数据验证
 * @author DougLei
 */
public class XmlColumnMetadataValidate implements MetadataValidate{
	private static final Logger logger = LoggerFactory.getLogger(XmlColumnMetadataValidate.class);

	public Metadata doValidate(Object obj) throws MetadataValidateException{
		return doValidate((Element)obj);
	}
	
	private ColumnMetadata doValidate(Element element) {
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			throw new MetadataValidateException("<column>元素的name属性值不能为空");
		}
		DataTypeHandler dataTypeHandler = validateDataType(element.attributeValue("dataType"));
		ColumnMetadata column = new ColumnMetadata(name, dataTypeHandler, element.attributeValue("property"), validateIsPrimaryKey(element.attributeValue("primaryKey")));
		
		if(CurrentTableIsCreate.isCreate()) {
			setExtendColumnProperty(column, element);
		}
		return column;
	}

	private DataTypeHandler validateDataType(String dataType) throws MetadataValidateException {
		if(StringUtil.isEmpty(dataType)) {
			if(logger.isDebugEnabled()) {
				logger.debug("配置的dataType为空, 使用默认的DataTypeHandler={}", AbstractStringDataTypeHandler.class.getName());
			}
			return DBRunEnvironmentContext.getDialect().getDataTypeHandlerMapping().getDefaultDataTypeHandler();
		}
		try {
			return DBRunEnvironmentContext.getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByCode(dataType);
		} catch (Exception e) {
			throw new MetadataValidateException("<column>元素的dataType属性值异常: " + e.getMessage());
		}
	}
	
	private boolean validateIsPrimaryKey(String primaryKey) {
		return Boolean.parseBoolean(primaryKey);
	}
	
	
	private void setExtendColumnProperty(ColumnMetadata column, Element element) {
		// TODO Auto-generated method stub
		
		
		column.setColumnProperty(null);
	}
}
