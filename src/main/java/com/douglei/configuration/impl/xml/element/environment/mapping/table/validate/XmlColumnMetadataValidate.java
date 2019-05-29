package com.douglei.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.database.dialect.datatype.DBDataType;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractStringDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.utils.StringUtil;
import com.douglei.utils.datatype.ValidationUtil;

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
		DBRunEnvironmentContext.getDialect().getDBObjectNameHandler().validateDBObjectName(name);
		
		ClassDataTypeHandler dataType = getDataType(element.attributeValue("dataType"));
		DBDataType dbDataType = dataType.defaultDBDataType();
		
		String value = element.attributeValue("length");
		short length = ValidationUtil.isShort(value)?Short.parseShort(value):0;
		value = element.attributeValue("precision");
		short precision = ValidationUtil.isShort(value)?Short.parseShort(value):0;
		value = element.attributeValue("nullabled");
		boolean nullabled = ValidationUtil.isBoolean(value)?Boolean.parseBoolean(value):true;
		
		return new ColumnMetadata(element.attributeValue("property"), dataType, 
				name, dbDataType.fixInputLength(length), dbDataType.fixInputPrecision(length, precision), nullabled,
				Boolean.parseBoolean(element.attributeValue("primaryKey")), Boolean.parseBoolean(element.attributeValue("unique")), element.attributeValue("defaultValue"),
				Boolean.parseBoolean(element.attributeValue("validateData"))
				);
	}
	
	private ClassDataTypeHandler getDataType(String dataType) throws MetadataValidateException {
		if(StringUtil.isEmpty(dataType)) {
			if(logger.isDebugEnabled()) {
				logger.debug("配置的dataType为空, 使用默认的DataTypeHandler={}", AbstractStringDataTypeHandler.class.getName());
			}
			return DBRunEnvironmentContext.getDialect().getDataTypeHandlerMapping().getDefaultClassDataTypeHandler();
		}
		try {
			return DBRunEnvironmentContext.getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByCode(dataType);
		} catch (Exception e) {
			throw new MetadataValidateException("<column>元素的dataType属性值异常: " + e.getMessage());
		}
	}
}
