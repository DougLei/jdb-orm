package com.douglei.orm.mapping.validator;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ParameterNode;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;

/**
 * 
 * @author DougLei
 */
public class ValidatedData {
	// 被验证的数据值
	private Object value; 
	
	// 被验证的数据值的相关信息
	private String name; // 名称
	private DBDataType dbDataType;// 数据类型
	private int length;// 长度
	private int precision;// 精度
	private boolean nullable;// 是否可为空

	/**
	 * 设置验证的(列)数据
	 * @param value
	 * @param column
	 */
	public void setValue(Object value, ColumnMetadata column) {
		this.value = value;
		this.name = column.getCode();
		this.dbDataType = column.getDBDataType();
		this.length = column.getLength();
		this.precision = column.getPrecision();
		this.nullable = column.isNullable();
	}
	
	/**
	 * 设置验证的(sql参数)数据
	 * @param value
	 * @param parameter
	 */
	public void setValue(Object value, ParameterNode parameter) {
		this.value = value;
		this.name = parameter.getName();
		this.dbDataType = parameter.getDBDataType();
		this.length = parameter.getLength();
		this.precision = parameter.getPrecision();
		this.nullable = parameter.isNullable();
	}
	
	/**
	 * 获取被验证的数据值
	 * @return
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * 获取名称
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 获取DB数据类型
	 * @return
	 */
	public DBDataType getDBDataType() {
		return dbDataType;
	}
	/**
	 * 获取数据类型长度
	 * @return
	 */
	public int getLength() {
		return length;
	}
	/**
	 * 获取数据类型精度
	 * @return
	 */
	public int getPrecision() {
		return precision;
	}
	/**
	 * 获取是否可为空
	 * @return
	 */
	public boolean isNullable() {
		return nullable;
	}
}
