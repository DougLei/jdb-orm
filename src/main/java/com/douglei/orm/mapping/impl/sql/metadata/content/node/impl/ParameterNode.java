package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;

/**
 * 
 * @author DougLei
 */
public class ParameterNode implements SqlNode{
	private transient String configText;
	
	private String name;// 参数名
	private ParameterMode mode;// 输入输出类型
	private String dbDataType;// 数据类型
	private transient DBDataType DBDataType; // 数据类型
	private int length;// 长度
	private int precision;// 精度
	
	private boolean nullable;// 是否可为空
	private String defaultValue;// 默认值
	
	private boolean placeholder;// 是否使用占位符?
	private String prefix;// 非占位符时的前缀字符
	private String suffix;// 非占位符时的后缀字符
	
	public ParameterNode(String configText) {
		this.configText = configText;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMode(ParameterMode mode) {
		this.mode = mode;
	}
	public void setDBDataType(DBDataType DBDataType) {
		this.dbDataType = DBDataType.getName();
		this.DBDataType = DBDataType;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public void setPlaceholder(boolean placeholder) {
		this.placeholder = placeholder;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.PARAMETER;
	}
	
	public String getConfigText() {
		return configText;
	}
	public ParameterMode getMode() {
		return mode;
	}
	public String getName() {
		return name;
	}
	public DBDataType getDBDataType() {
		if(DBDataType == null)
			DBDataType = EnvironmentContext.getEnvironment().getDialect().getDataTypeContainer().getDBDataTypeByName(dbDataType);
		return DBDataType;
	}
	public int getLength() {
		return length;
	}
	public int getPrecision() {
		return precision;
	}
	public boolean isPlaceholder() {
		return placeholder;
	}
	public String getPrefix() {
		return prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public boolean isNullable() {
		return nullable;
	}
	
	@Override
	public String toString() {
		return "[name=" + name + ", mode="+mode+", dbDataType=" + dbDataType + "]";
	}
}
