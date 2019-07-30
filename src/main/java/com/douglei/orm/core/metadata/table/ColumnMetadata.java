package com.douglei.orm.core.metadata.table;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.DataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.naming.converter.ConverterUtil;
import com.douglei.tools.utils.naming.converter.impl.ColumnName2PropertyNameConverter;

/**
 * 列元数据
 * @author DougLei
 */
public class ColumnMetadata implements Metadata{
	
	private String name;// 列名
	private String property;// 映射的代码类中的属性名
	
	private String oldName;// 旧列名
	private String descriptionName;// 描述名
	private DataType dataType;// 数据类型
	private short length;// 长度
	private short precision;// 精度
	boolean nullabled;// 是否可为空
	boolean primaryKey;// 是否是主键
	boolean unique;// 是否唯一
	String defaultValue;// 默认值
	String check;// 检查约束表达式
	String fkTableName;// 外键约束关联的表名
	String fkColumnName;// 外键约束关联的列名
	private boolean validate;// 是否验证
	
	private ClassDataTypeHandler dataTypeHandler;// dataType处理器, 根据dataType得到
	private DBDataType dbDataType;// 数据库的数据类型, 根据dataTypeHandler得到
	
	public ColumnMetadata(String property, String name, String oldName, String descriptionName, String dataType, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue, String check, String fkTableName, String fkColumnName, boolean validate) {
		setNameByValidate(name, oldName);
		this.dataType = DataType.toValue(dataType);
		processDataType(dataType);
		processOtherPropertyValues(descriptionName, length, precision, nullabled, primaryKey, unique, defaultValue, check, fkTableName, fkColumnName, validate);
		this.property = StringUtil.isEmpty(property)?null:property;
	}
	
	// 设置name的同时, 对name进行验证
	private void setNameByValidate(String name, String oldName) {
		DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getDBObjectHandler().validateDBObjectName(name);
		this.name = name.toUpperCase();
		if(StringUtil.isEmpty(oldName)) {
			this.oldName = this.name;
		}else {
			this.oldName = oldName.toUpperCase();
		}
	}
	
	// 处理dataTypeHandler和dbDataType的值
	private void processDataType(String dataType) {
		this.dataTypeHandler = DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByCode(this.dataType==null?dataType:this.dataType.getName());
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
	
	// 处理主键和是否为空和是否唯一的值
	private void processPrimaryKeyAndNullabledAndUnique(boolean primaryKey, boolean nullabled, boolean unique) {
		this.primaryKey = primaryKey;
		if(primaryKey) {
			this.nullabled = false;
			this.unique = false;// 如果是主键, 则不需要设置唯一
			this.defaultValue = null;// 如果是主键, 则不能有默认值
		}else {
			this.nullabled = nullabled;
			this.unique = unique;
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
	
	// 处理长度和精度的值
	private void processLengthAndPrecision(short length, short precision) {
		this.length = dbDataType.fixInputLength(length);
		this.precision = dbDataType.fixInputPrecision(this.length, precision);
	}
	
	/**
	 * <pre>
	 * 	修正propertyName的值
	 * 	如果没有配置类名, 则属性名必须不存在, 如果配置了就置空, 没有配置就不处理
	 * 	如果配置了类名, 则属性名必须存在, 如果配置了就使用, 没有配置, 就将列名转换为属性名
	 * </pre>
	 * @param classNameEmpty
	 */
	public void correctProperty(boolean classNameEmpty) {
		if(classNameEmpty && property != null) {
			property = null;
		}else if(!classNameEmpty && property == null){
			property = ConverterUtil.convert(name, ColumnName2PropertyNameConverter.class);
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
	public boolean isValidate() {
		return validate;
	}
	
	/**
	 * <b>【慎用】</b>
	 * 该方法直接修改了name属性的值, 没有任何理由
	 * @param name
	 */
	public void forceUpdateName(String name) {
		this.name = name;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.COLUMN;
	}
}
