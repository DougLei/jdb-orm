package com.douglei.database.metadata.table;

import com.douglei.database.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;
import com.douglei.database.utils.NamingUtil;
import com.douglei.utils.StringUtil;

/**
 * 列元数据
 * @author DougLei
 */
public class ColumnMetadata implements Metadata{
	
	private String property;// 映射的代码类中的属性名
	private ClassDataTypeHandler dataType;// 数据类型
	
	private String name;// 列名
	private short length;// 长度
	private short precision;// 精度
	private boolean nullabled;// 是否可为空
	private boolean primaryKey;// 是否是主键
	private boolean unique;// 是否唯一
	private String defaultValue;// 默认值
	private boolean validateData;// 是否验证数据, 即是否对传入的数据进行验证
	
	public ColumnMetadata(String property, ClassDataTypeHandler dataType, 
			String name, short length, short precision, boolean nullabled, 
			boolean primaryKey, boolean unique, String defaultValue, 
			boolean validateData) {
		this.dataType = dataType;
		this.name = name.toUpperCase();
		this.length = length;
		this.precision = precision;
		this.unique = unique;// TODO oracle mysql当字段可为空且唯一时, 可以插入多个null, 即null不做为相同的数据, 而sqlserver会将null也做重复判断, 抛出异常
		this.defaultValue = defaultValue;
		this.validateData = validateData;
		
		setPropery(property);
		setPrimaryKeyAndNullabled(primaryKey, nullabled);
		setCode();
	}
	
	// 
	private void setPropery(String property) {
		if(StringUtil.notEmpty(property)) {
			this.property = property;
		}
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
		return code;
	}
	private String code;
	private void setCode() {
		if(property == null) {
			code = name;
		}else {
			code = property;
		}
	}
	
	/**
	 * <pre>
	 * 	修正propertyName的值
	 * 	如果没有配置类名, 则属性名必须不存在, 如果配置了就置空, 没有配置就不处理
	 * 	如果配置了类名, 则属性名必须存在, 如果配置了就使用, 没有配置, 就将列名转换为属性名
	 * </pre>
	 * @param classNameIsNull
	 */
	public void fixPropertyNameValue(boolean classNameIsNull) {
		if(classNameIsNull) {
			if(property != null) {
				property = null;
				setCode();
			}
		}else {
			if(property == null) {
				property = NamingUtil.columnName2PropertyName(name);
				setCode();
			}
		}
	}
	
	// 
	public void setPrimaryKeyAndNullabled(boolean primaryKey, boolean nullabled) {
		this.primaryKey = primaryKey;
		if(primaryKey) {
			this.nullabled = false;
		}else {
			this.nullabled = nullabled;
		}
	}
	
	public String getProperty() {
		return property;
	}
	public ClassDataTypeHandler getDataType() {
		return dataType;
	}
	public String getName() {
		return name;
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
	public boolean isValidateData() {
		return validateData;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.COLUMN;
	}
}
