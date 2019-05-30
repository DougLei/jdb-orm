package com.douglei.database.dialect.db.table.entity;

import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.database.dialect.datatype.DBDataType;
import com.douglei.database.dialect.datatype.DataType;
import com.douglei.database.dialect.datatype.handler.classtype.ClassDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class Column {
	
	protected String name;// 列名
	protected DataType dataType;// 数据类型
	protected short length;// 长度
	protected short precision;// 精度
	protected boolean nullabled;// 是否可为空
	protected boolean primaryKey;// 是否是主键
	protected boolean unique;// 是否唯一
	protected String defaultValue;// 默认值
	
	protected ClassDataTypeHandler dataTypeHandler;// dataType处理器, 根据dataType得到
	protected DBDataType dbDataType;// 数据库的数据类型, 根据dataTypeHandler得到
	
	private Column(String name) {
		DBRunEnvironmentContext.getDialect().getDBObjectNameHandler().validateDBObjectName(name);
	}
	
	public Column(String name, DataType dataType, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue) {
		this(name);
		this.dataType = dataType;
		processDataType(null);
		processOtherPropertyValues(name, length, precision, nullabled, primaryKey, unique, defaultValue);
	}
	
	public Column(String name, Class<? extends ClassDataTypeHandler> dataType, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue) {
		this(name);
		processDataType(dataType.getName());
		processOtherPropertyValues(name, length, precision, nullabled, primaryKey, unique, defaultValue);
	}
	
	public Column(String name, String dataType, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue) {
		this(name);
		this.dataType = DataType.toValue(dataType);
		processDataType(dataType);
		processOtherPropertyValues(name, length, precision, nullabled, primaryKey, unique, defaultValue);
	}
	
	// 处理dataTypeHandler和dbDataType的值
	private void processDataType(String dataType) {
		this.dataTypeHandler = DBRunEnvironmentContext.getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByCode(this.dataType==null?dataType:this.dataType.getName());
		this.dbDataType = dataTypeHandler.defaultDBDataType();
	}
	
	// 处理其他属性值
	private void processOtherPropertyValues(String name, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue) {
		this.name = name.toUpperCase();
		this.unique = unique;
		this.defaultValue = defaultValue;
		processLengthAndPrecision(length, precision);
		processPrimaryKeyAndNullabled(primaryKey, nullabled);
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
}
