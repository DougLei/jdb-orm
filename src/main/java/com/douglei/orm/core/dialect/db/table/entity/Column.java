package com.douglei.orm.core.dialect.db.table.entity;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class Column {
	
	protected String name;// 列名
	protected String descriptionName;// 描述名
	protected DataType dataType;// 数据类型
	protected short length;// 长度
	protected short precision;// 精度
	protected boolean nullabled;// 是否可为空
	protected boolean primaryKey;// 是否是主键
	protected boolean unique;// 是否唯一
	protected String defaultValue;// 默认值
	protected boolean validate;// 是否验证
	
	protected ClassDataTypeHandler dataTypeHandler;// dataType处理器, 根据dataType得到
	protected DBDataType dbDataType;// 数据库的数据类型, 根据dataTypeHandler得到
	
	public Column(String name, String descriptionName, DataType dataType, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue, boolean validate) {
		setNameByValidate(name);
		this.dataType = dataType;
		processDataType(null);
		processOtherPropertyValues(descriptionName, length, precision, nullabled, primaryKey, unique, defaultValue, validate);
	}
	public Column(String name, String descriptionName, Class<? extends ClassDataTypeHandler> dataType, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue, boolean validate) {
		setNameByValidate(name);
		processDataType(dataType.getName());
		processOtherPropertyValues(descriptionName, length, precision, nullabled, primaryKey, unique, defaultValue, validate);
	}
	public Column(String name, String descriptionName, String dataType, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue, boolean validate) {
		setNameByValidate(name);
		this.dataType = DataType.toValue(dataType);
		processDataType(dataType);
		processOtherPropertyValues(descriptionName, length, precision, nullabled, primaryKey, unique, defaultValue, validate);
	}
	
	// 处理dataTypeHandler和dbDataType的值
	private void processDataType(String dataType) {
		this.dataTypeHandler = DBRunEnvironmentContext.getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByCode(this.dataType==null?dataType:this.dataType.getName());
		this.dbDataType = dataTypeHandler.defaultDBDataType();
	}
	
	// 处理其他属性值
	private void processOtherPropertyValues(String descriptionName, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue, boolean validate) {
		if(StringUtil.isEmpty(descriptionName)) {
			descriptionName = name;
		}
		this.descriptionName = descriptionName;
		this.defaultValue = defaultValue;
		processLengthAndPrecision(length, precision);
		processPrimaryKeyAndNullabled(primaryKey, nullabled);
		this.validate = validate;
	}
	
	// 处理长度和精度的值
	private void processLengthAndPrecision(short length, short precision) {
		this.length = dbDataType.fixInputLength(length);
		this.precision = dbDataType.fixInputPrecision(this.length, precision);
	}

	// 处理主键和是否为空的值
	protected void processPrimaryKeyAndNullabled(boolean primaryKey, boolean nullabled) {
		this.primaryKey = primaryKey;
		if(primaryKey) {
			this.nullabled = false;
		}else {
			this.nullabled = nullabled;
		}
	}
	
	
	public String getName() {
		return name;
	}
	public DataType getDataType() {
		return dataType;
	}
	public boolean isPrimaryKey() {
		return primaryKey;
	}
	public short getLength() {
		return length;
	}
	public short getPrecision() {
		return precision;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public boolean isUnique() {
		return unique;
	}
	public boolean isNullabled() {
		return nullabled;
	}
	public ClassDataTypeHandler getDataTypeHandler() {
		return dataTypeHandler;
	}
	public DBDataType getDBDataType() {
		return dbDataType;
	}
	public void setNameByValidate(String name) {
		DBRunEnvironmentContext.getDialect().getDBObjectNameHandler().validateDBObjectName(name);
		this.name = name.toUpperCase();
	}
	public boolean isValidate() {
		return validate;
	}
}