package com.douglei.orm.core.dialect.db.table.entity;

import java.io.Serializable;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class Column implements Serializable{
	private static final long serialVersionUID = -5921863830755560622L;
	
	protected String name;// 列名
	protected String oldName;// 旧列名
	protected String descriptionName;// 描述名
	protected DataType dataType;// 数据类型
	protected short length;// 长度
	protected short precision;// 精度
	protected boolean nullabled;// 是否可为空
	protected boolean primaryKey;// 是否是主键
	protected boolean unique;// 是否唯一
	protected String defaultValue;// 默认值
	protected String check;// 检查约束表达式
	protected String fkTableName;// 外键约束关联的表名
	protected String fkColumnName;// 外键约束关联的列名
	protected boolean validate;// 是否验证
	
	protected ClassDataTypeHandler dataTypeHandler;// dataType处理器, 根据dataType得到
	protected DBDataType dbDataType;// 数据库的数据类型, 根据dataTypeHandler得到
	
	public Column(String name, String oldName, String descriptionName, DataType dataType, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue, String check, String fkTableName, String fkColumnName, boolean validate) {
		setNameByValidate(name, oldName);
		this.dataType = dataType;
		processDataType(null);
		processOtherPropertyValues(descriptionName, length, precision, nullabled, primaryKey, unique, defaultValue, check, fkTableName, fkColumnName, validate);
	}
	public Column(String name, String oldName, String descriptionName, Class<? extends ClassDataTypeHandler> dataType, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue, String check, String fkTableName, String fkColumnName, boolean validate) {
		setNameByValidate(name, oldName);
		processDataType(dataType.getName());
		processOtherPropertyValues(descriptionName, length, precision, nullabled, primaryKey, unique, defaultValue, check, fkTableName, fkColumnName, validate);
	}
	public Column(String name, String oldName, String descriptionName, String dataType, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue, String check, String fkTableName, String fkColumnName, boolean validate) {
		setNameByValidate(name, oldName);
		this.dataType = DataType.toValue(dataType);
		processDataType(dataType);
		processOtherPropertyValues(descriptionName, length, precision, nullabled, primaryKey, unique, defaultValue, check, fkTableName, fkColumnName, validate);
	}
	
	// 处理dataTypeHandler和dbDataType的值
	private void processDataType(String dataType) {
		this.dataTypeHandler = DBRunEnvironmentContext.getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByCode(this.dataType==null?dataType:this.dataType.getName());
		this.dbDataType = dataTypeHandler.getDBDataType();
	}
	
	// 处理其他属性值
	private void processOtherPropertyValues(String descriptionName, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue, String check, String fkTableName, String fkColumnName, boolean validate) {
		if(StringUtil.isEmpty(descriptionName)) {
			descriptionName = name;
		}
		this.descriptionName = descriptionName;
		this.defaultValue = defaultValue;
		processLengthAndPrecision(length, precision);
		processPrimaryKeyAndNullabledAndUnique(primaryKey, nullabled, unique);
		processCheckAndForeignKey(check, fkTableName, fkColumnName);
		this.validate = validate;
	}
	
	// 处理长度和精度的值
	private void processLengthAndPrecision(short length, short precision) {
		this.length = dbDataType.fixInputLength(length);
		this.precision = dbDataType.fixInputPrecision(this.length, precision);
	}

	// 处理主键和是否为空和是否唯一的值
	protected void processPrimaryKeyAndNullabledAndUnique(boolean primaryKey, boolean nullabled, boolean unique) {
		this.primaryKey = primaryKey;
		if(primaryKey) {
			this.nullabled = false;
			this.unique = false;// 如果是主键, 则不需要设置唯一
			clearDefaultValue();// 如果是主键, 则不能有默认值
		}else {
			this.nullabled = nullabled;
			this.unique = unique;
		}
	}
	
	// 清空默认值
	void clearDefaultValue() {
		if(this.defaultValue != null) {
			this.defaultValue = null;
		}
	}
	
	// 处理检查约束值和外键约束值
	private void processCheckAndForeignKey(String check, String fkTableName, String fkColumnName) {
		if(StringUtil.notEmpty(check)) {
			this.check = check;
		}
		if(StringUtil.notEmpty(fkTableName) && StringUtil.notEmpty(fkColumnName)) {
			this.fkTableName = fkTableName;
			this.fkColumnName = fkColumnName;
		}
	}
	
	public String getName() {
		return name;
	}
	public String getOldName() {
		return oldName;
	}
	public String getDescriptionName() {
		return descriptionName;
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
	public boolean isUnique() {
		return unique;
	}
	public boolean isNullabled() {
		return nullabled;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public String getCheck() {
		return check;
	}
	public String getFkTableName() {
		return fkTableName;
	}
	public String getFkColumnName() {
		return fkColumnName;
	}
	public ClassDataTypeHandler getDataTypeHandler() {
		return dataTypeHandler;
	}
	public DBDataType getDBDataType() {
		return dbDataType;
	}
	public void setNameByValidate(String name, String oldName) {
		DBRunEnvironmentContext.getDialect().getDBObjectNameHandler().validateDBObjectName(name);
		this.name = name.toUpperCase();
		
		if(StringUtil.isEmpty(oldName)) {
			this.oldName = this.name;
		}else {
			this.oldName = oldName.toUpperCase();
		}
	}
	public boolean isValidate() {
		return validate;
	}
	
	/**
	 * <b>【慎用】</b>
	 * 更新列名
	 * 该方法直接修改了name属性的值
	 * @param name
	 */
	
	public void _danger2UpdateColumnName(String name) {
		this.name = name;
	}
}
