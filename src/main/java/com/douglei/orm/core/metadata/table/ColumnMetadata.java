package com.douglei.orm.core.metadata.table;

import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.orm.core.metadata.validator.ValidatorHandler;
import com.douglei.orm.core.metadata.validator.internal._DataTypeValidator;
import com.douglei.tools.utils.StringUtil;

/**
 * 列元数据
 * @author DougLei
 */
public class ColumnMetadata implements Metadata{
	private static final long serialVersionUID = 7713395662973764848L;
	private String name;// 列名
	private String property;// 映射的代码类中的属性名
	
	private String oldName;// 旧列名
	private String descriptionName;// 描述名
	private short length;// 长度
	private short precision;// 精度
	private boolean nullable;// 是否可为空
	private boolean primaryKey;// 是否是主键
	private boolean unique;// 是否唯一
	private String defaultValue;// 默认值
	private String check;// 检查约束表达式
	private String fkTableName;// 外键约束关联的表名
	private String fkColumnName;// 外键约束关联的列名
	private boolean validate;// 是否验证
	
	private ClassDataTypeHandler dataTypeHandler;// dataType处理器, 根据dataType得到
	private DBDataType dbDataType;// 数据库的数据类型, 根据dataTypeHandler得到
	
	private boolean isPrimaryKeySequence;// 是否是主键序列
	
	private ValidatorHandler validatorHandler;// 验证器
	
	public ColumnMetadata(String property, String name, String oldName, String descriptionName, String dataType, short length, short precision, boolean nullable, boolean primaryKey, boolean unique, String defaultValue, String check, String fkTableName, String fkColumnName, boolean validate) {
		setNameByValidate(name, oldName);
		
		this.property = StringUtil.isEmpty(property)?null:property;
		this.descriptionName = StringUtil.isEmpty(descriptionName)?name:descriptionName;
		this.nullable = nullable;
		this.validate = validate;
		set2DefaultValue(defaultValue);
		set2CheckConstraint(check);
		set2ForeginKeyConstraint(fkTableName, fkColumnName);
		
		processDataType(DataType.toValue(dataType), dataType, length, precision);
		set2UniqueConstraint(unique);
		set2PrimaryKeyConstraint(primaryKey);
	}
	
	// 设置name的同时, 对name进行验证
	private void setNameByValidate(String name, String oldName) {
		EnvironmentContext.getDialect().getDBObjectHandler().validateDBObjectName(name);
		this.name = name.toUpperCase();
		if(StringUtil.isEmpty(oldName)) {
			this.oldName = this.name;
		}else {
			this.oldName = oldName.toUpperCase();
		}
	}
	
	// 处理数据类型
	private void processDataType(DataType dataType, String dataType_, short length, short precision) {
		this.dataTypeHandler = EnvironmentContext.getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByCode(dataType==null?dataType_:dataType.getName());
		this.dbDataType = dataTypeHandler.getDBDataType();
		this.length = dbDataType.correctInputLength(length);
		this.precision = dbDataType.correctInputPrecision(this.length, precision);
	}
	
	// 设置主键约束
	public void set2PrimaryKeyConstraint(boolean primaryKey) {
		this.primaryKey = primaryKey;
		if(primaryKey) {
			this.nullable = false;// 如果是主键, 则不能为空
			this.unique = false;// 如果是主键, 则不需要设置唯一
			this.defaultValue = null;// 如果是主键, 则不能有默认值
			this.check = null;// 如果是主键, 则不能有检查约束
			this.fkTableName = null;// 如果是主键, 则不能有外键约束
			this.fkColumnName = null;
		}
	}
	
	/**
	 * 设置为唯一约束
	 * @param
	 */
	public void set2UniqueConstraint(boolean unique) {
		this.unique = unique;
		if(unique) {
			this.nullable = false;// 如果有唯一约束, 则不能为空
			this.defaultValue = null;// 如果有唯一约束, 则不能有默认值
		}
	}
	
	/**
	 * 设置默认值
	 * @param defaultValue
	 */
	public void set2DefaultValue(String defaultValue) {
		if(defaultValue != null) {
			this.defaultValue = defaultValue;
		}
	}
	
	/**
	 * 设置检查约束
	 * @param checkConstraint
	 */
	public void set2CheckConstraint(String checkConstraint) {
		if(StringUtil.notEmpty(checkConstraint)) {
			this.check = checkConstraint;
		}
	}
	
	/**
	 * 设置外键约束
	 * @param fkTableName
	 * @param fkColumnName
	 */
	public void set2ForeginKeyConstraint(String fkTableName, String fkColumnName) {
		if(StringUtil.notEmpty(fkTableName) && StringUtil.notEmpty(fkColumnName)) {
			this.fkTableName = fkTableName;
			this.fkColumnName = fkColumnName;
		}
	}
	
	/**
	 * 设置该列为主键序列
	 */
	public void set2PrimaryKeySequence() {
		this.isPrimaryKeySequence = true;
	}
	
	/**
	 * 【慎用】该方法直接强制修改了name属性的值, 目前只在同步表时使用过, 能不用绝对不要用
	 * @param name
	 */
	public void forceUpdateName(String name) {
		this.name = name;
	}
	
	/**
	 * 设置验证器
	 * @param existsPrimaryKeyHandler 是否存在主键处理器, 如果存在, 则主键可以为空
	 * @param validatorHandler
	 */
	public ColumnMetadata setValidatorHandler(boolean existsPrimaryKeyHandler, ValidatorHandler validatorHandler) {
		if(validate || validatorHandler.byConfig()) {
			this.validate = true;
			this.validatorHandler = validatorHandler;
			this.validatorHandler.setNullableValidator((primaryKey && existsPrimaryKeyHandler)?true:(defaultValue==null?nullable:true));
			this.validatorHandler.addValidator(new _DataTypeValidator(getDataTypeHandler(), length, precision));
			return this;
		}
		return null;
	}
	
	/**
	 * <pre>
	 * 	如果指定了propertyName, 则返回propertyName
	 * 	否则返回name, 即列名
	 * </pre>
	 * @return
	 */
	@Override
	public String getCode() {
		if(property == null) {
			return name;
		}else {
			return property;
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
	public String getProperty() {
		return property;
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
	public boolean isNullable() {
		return nullable;
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
	public boolean isValidate() {
		return validate;
	}
	public boolean isPrimaryKeySequence() {
		return isPrimaryKeySequence;
	}
	public ValidatorHandler getValidatorHandler() {
		return validatorHandler;
	}

	@Override
	public MetadataType getMetadataType() {
		return MetadataType.COLUMN;
	}
}
