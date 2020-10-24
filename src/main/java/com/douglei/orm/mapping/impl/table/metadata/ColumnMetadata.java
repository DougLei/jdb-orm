package com.douglei.orm.mapping.impl.table.metadata;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.mapping.metadata.validator.ValidateHandler;
import com.douglei.orm.mapping.metadata.validator.impl._DataTypeValidator;
import com.douglei.orm.mapping.metadata.validator.impl._NullableValidator;
import com.douglei.tools.utils.StringUtil;

/**
 * 列元数据
 * @author DougLei
 */
public class ColumnMetadata extends AbstractMetadata {
	private static final long serialVersionUID = -7093225708443633905L;

	private String property;// 映射的代码类中的属性名
	
	private DBDataType dbDataType;// 数据类型
	private int length;// 长度
	private int precision;// 精度
	private boolean nullable;// 是否可为空
	private boolean isPrimaryKey;// 是否是主键
	private boolean unique;// 是否唯一
	private String defaultValue;// 默认值
	private String check;// 检查约束表达式
	private String fkTableName;// 外键约束关联的表名
	private String fkColumnName;// 外键约束关联的列名
	private boolean validate;// 是否验证
	private String description;// 描述
	
	private boolean isPrimaryKeySequence;// 是否是主键序列
	private ValidateHandler validateHandler;// 验证器
	
	public ColumnMetadata(String property, String name, String oldName, DBDataType dbDataType, int length, int precision, boolean nullable, boolean primaryKey, boolean unique, String defaultValue, String check, String fkTableName, String fkColumnName, boolean validate, String description) {
		super(name, oldName);
		
		this.property = StringUtil.isEmpty(property)?null:property;
		this.dbDataType = dbDataType;
		this.length = length;
		this.precision = precision;
		
		this.nullable = nullable;
		this.validate = validate;
		setDefaultValue(defaultValue);
		setCheckConstraint(check);
		setForeginKeyConstraint(fkTableName, fkColumnName);
		
		setUniqueConstraint(unique);
		setPrimaryKeyConstraint(primaryKey);
		this.description = StringUtil.isEmpty(description)?name:description;
	}
	
	// 设置主键约束
	public void setPrimaryKeyConstraint(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
		if(isPrimaryKey) {
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
	public void setUniqueConstraint(boolean unique) {
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
	public void setDefaultValue(String defaultValue) {
		if(defaultValue != null) 
			this.defaultValue = defaultValue;
	}
	
	/**
	 * 设置检查约束
	 * @param checkConstraint
	 */
	public void setCheckConstraint(String checkConstraint) {
		if(StringUtil.notEmpty(checkConstraint)) 
			this.check = checkConstraint;
	}
	
	/**
	 * 设置外键约束
	 * @param fkTableName
	 * @param fkColumnName
	 */
	public void setForeginKeyConstraint(String fkTableName, String fkColumnName) {
		if(StringUtil.notEmpty(fkTableName) && StringUtil.notEmpty(fkColumnName)) {
			this.fkTableName = fkTableName;
			this.fkColumnName = fkColumnName;
		}
	}
	
	/**
	 * 设置该列为主键序列
	 */
	public void setPrimaryKeySequence() {
		this.isPrimaryKeySequence = true;
	}
	
	/**
	 * 设置验证器
	 * @param existsPrimaryKeyHandler 是否存在主键处理器, 如果存在, 则主键可以为空
	 * @param validateHandler
	 */
	public ColumnMetadata setValidateHandler(boolean existsPrimaryKeyHandler, ValidateHandler validateHandler) {
		if(validate && validateHandler == null) 
			validateHandler = new ValidateHandler(name);
		if(validateHandler != null) {
			this.validate = true;
			this.validateHandler = validateHandler;
			this.validateHandler.addValidator(new _NullableValidator((isPrimaryKey && existsPrimaryKeyHandler)?true:(defaultValue==null?nullable:true)));
			this.validateHandler.addValidator(new _DataTypeValidator(dbDataType, length, precision));
			this.validateHandler.sort();
			return this;
		}
		return null;
	}
	
	/**
	 * 如果指定了propertyName, 则返回propertyName; 否则返回name, 即列名
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
	
	public String getDescription() {
		return description;
	}
	public String getProperty() {
		return property;
	}
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	public int getLength() {
		return length;
	}
	public int getPrecision() {
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
	public DBDataType getDBDataType() {
		return dbDataType;
	}
	public boolean isValidate() {
		return validate;
	}
	public boolean isPrimaryKeySequence() {
		return isPrimaryKeySequence;
	}
	public ValidateHandler getValidateHandler() {
		return validateHandler;
	}
}
