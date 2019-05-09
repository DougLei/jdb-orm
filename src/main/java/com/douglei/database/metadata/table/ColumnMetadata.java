package com.douglei.database.metadata.table;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;
import com.douglei.database.utils.NamingUtil;
import com.douglei.utils.StringUtil;

/**
 * 列元数据
 * @author DougLei
 */
public class ColumnMetadata implements Metadata{
	
	/**
	 * 列名
	 */
	private String name;
	/**
	 * 映射的代码类中的属性名
	 */
	private String propertyName;
	private boolean propertyNameIsNull;
	
	/**
	 * 数据类型
	 */
	private DataTypeHandler dataTypeHandler;
	
	/**
	 * 是否是懒加载
	 * <pre>
	 * 	默认流字段, 就是懒加载
	 * </pre>
	 */
	private boolean lazyload;
	
	public ColumnMetadata(String name, DataTypeHandler dataTypeHandler, String propertyName, boolean lazyload) {
		this.name = name;
		this.dataTypeHandler = dataTypeHandler;
		this.lazyload = lazyload;
		setPropertyName(propertyName);
		setCode();
	}
	private void setPropertyName(String propertyName) {
		if(StringUtil.isEmpty(propertyName)) {
			propertyNameIsNull = true;
			if(propertyName != null) {
				propertyName = null;
			}
		}
		this.propertyName = propertyName;
	}
	
	/**
	 * <pre>
	 * 	如果指定了propertyName, 则返回propertyName
	 * 	否则返回name, 即列名
	 * </pre>
	 * @return
	 */
	public String getCode() {
		return code;
	}
	private String code;
	private void setCode() {
		if(propertyName == null) {
			code = name;
		}else {
			code = propertyName;
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
			if(!propertyNameIsNull) {
				propertyNameIsNull = true;
				propertyName = null;
			}
		}else {
			if(propertyNameIsNull) {
				propertyNameIsNull = false;
				propertyName = NamingUtil.columnName2PropertyName(name);
			}
		}
	}
	
	public String getName() {
		return name;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public DataTypeHandler getDataType() {
		return dataTypeHandler;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.COLUMN;
	}
	
	/**
	 * <pre>
	 * 	是否支持自动查询
	 * 	即一些大字段, 是不会直接查询的, 如果需要查询大字段内容, 则只能写sql语句查询
	 * </pre>
	 * @return
	 */
	public boolean isLazyload() {
		return lazyload;
	}
}
