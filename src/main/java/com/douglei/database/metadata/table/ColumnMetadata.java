package com.douglei.database.metadata.table;

import com.douglei.database.datatype.DataType;
import com.douglei.database.metadata.Metadata;
import com.douglei.utils.StringUtil;

/**
 * 列元数据
 * @author DougLei
 */
public class ColumnMetadata extends Metadata{
	
	/**
	 * 列名
	 */
	private String name;
	/**
	 * 映射的代码类中的属性名
	 * <pre>
	 * 	如果没有配置，默认就和name的值一致
	 * </pre>
	 */
	private String propertyName;
	
	/**
	 * 数据类型
	 */
	private DataType dataType;
	
	public ColumnMetadata(String name, DataType dataType, String propertyName) {
		this.name = name;
		this.dataType = dataType;
		setPropertyName(propertyName);
	}
	private void setPropertyName(String propertyName) {
		if(StringUtil.isEmpty(propertyName)) {
			propertyName = name;
		}
		this.propertyName = propertyName;
	}

	public String getName() {
		return name;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public DataType getDataType() {
		return dataType;
	}
}
