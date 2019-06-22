package com.douglei.orm.core.metadata.table;

import com.douglei.orm.core.dialect.db.table.entity.Column;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.orm.core.utils.NamingUtil;
import com.douglei.tools.utils.StringUtil;

/**
 * 列元数据
 * @author DougLei
 */
public class ColumnMetadata extends Column implements Metadata{
	
	private String code;
	private String property;// 映射的代码类中的属性名
	
	public ColumnMetadata(String property, String name, String oldName, String descriptionName, String dataType, short length, short precision, boolean nullabled, boolean primaryKey, boolean unique, String defaultValue, String check, String fkTableName, String fkColumnName, boolean validate) {
		super(name, oldName, descriptionName, dataType, length, precision, nullabled, primaryKey, unique, defaultValue, check, fkTableName, fkColumnName, validate);
		setPropery(property);
		setCode();
	}
	private void setPropery(String property) {
		if(StringUtil.notEmpty(property)) {
			this.property = property;
		}
	}
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
	
	public String getProperty() {
		return property;
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
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.COLUMN;
	}
}
